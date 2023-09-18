package com.afecioru.study.cats.chapter2

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._


object App004_Monads extends App {

  val nums = List(1, 2, 3)
  val chars = List('a', 'b', 'c')

  // Exercise 1.1 - generate all combinations of num/char
  val combinations = for {
    num <- nums
    char <- chars
  } yield num -> char

  println("-----------[ OUTPUT 1 ]------------")
  println(combinations)
  println("----------------------------------")

  val numOption = Option(2)
  val charOption = Option('d')

  // Exercise 1.2 - same as above, but with different container
  val combinationOpt = for {
    num <- numOption
    char <- charOption
  } yield num -> char


  println("-----------[ OUTPUT 2 ]------------")
  println(combinationOpt)
  println("----------------------------------")

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val numFuture = Future {2}
  val charFuture = Future {'a'}

  // Exercise 1.3 - same as above, but with different container
  val combinationFuture = for {
    num <- numFuture
    char <- charFuture
  } yield num -> char

  println("-----------[ OUTPUT 3 ]------------")
  println(Await.result(combinationFuture, 1.second))
  println("----------------------------------")

  /*
   The monad pattern:
     - wrapping a value into a monadic value
     - the flatMap op. NOTE: don't think of this as an iteration, but a transformation
   */
  trait MyMonad[M[_]] {
    def pure[A](value: A): M[A] // the wrapping part
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]  // the flatMap part

    // Exercise 2: implement the map method (Monads are Functors)
    def map[A, B](ma: M[A])(f: A => B): M[B] = {
      flatMap(ma)(a => pure(f(a)))
    }
  }

  import cats.Monad
  import cats.instances.option._
  import cats.instances.list._
  import cats.instances.future._

  val optionMonad = Monad[Option]
  val anOption = optionMonad.pure(4) // Option(4)
  val aTransformedOption = optionMonad.flatMap(anOption) { x =>
    if (x % 3 == 0) Some(x + 1) else None
  }

  val listMonad = Monad[List]
  val aList = listMonad.pure(3)
  val aTransformedList = listMonad.flatMap(aList)(x => List(x, x + 10))

  // Exercise 2: use a Monad[Future]
  val futureMonad = Monad[Future]
  val aFuture = futureMonad.pure(5)
  val aTransformedFuture = futureMonad.flatMap(aFuture)(x => Future { x + 10 })

  println("-----------[ OUTPUT 4 ]------------")
  println(aTransformedOption)
  println(aTransformedList)
  println(Await.result(aTransformedFuture, 1.second))
  println("----------------------------------")

  // generalized API
  def generatePairs[M[_], A, B](m1: M[A], m2: M[B])
                               (implicit monad: Monad[M]): M[(A, B)] = {
     monad.flatMap(m1) { x =>
       // Since a Monad is a Functor we can use map
       monad.map(m2){y => x -> y}
     }
  }

  println("-----------[ OUTPUT 5 ]------------")
  println(generatePairs(List(1, 2, 3), List("a", "b", "c")))
  println(generatePairs(Option(1), Option("a")))
  println(Await.result(generatePairs(Future(1), Future("a")), 1.second))
  println("----------------------------------")


  // Extension methods (some specialized imports)
  import cats.syntax.applicative._ // pure is here
  import cats.syntax.functor._ // map is here
  import cats.syntax.flatMap._ // flatMap is here

  val anOption1 = 1.pure[Option] // implicit Monad[Option] is used => Some(1)
  val aTransformedOption1 = anOption.flatMap(x => (x + 1).pure[Option])

  // Since a Monad is a Functor we can use map
  val anotherTransformedOption = Monad[Option].map(anOption)(_ + 1)

  // Since we have map and flatMap, we can use for-comprehensions
  for {
    x <- 1.pure[List]
    y <- 2.pure[List]
  } yield x + y // List(3)

  // Exercise 4: implement getPairs using for-comps
  def gatPairs[M[_]: Monad, A, B](ma: M[A], mb: M[B]): M[(A, B)] = {
    for {
      a <- ma
      b <- mb
    } yield a -> b
  }

  println("-----------[ OUTPUT 6 ]------------")
  println(gatPairs(List(1, 2, 3), List("a", "b", "c")))
  println(gatPairs(Option(1), Option("a")))
  println(Await.result(gatPairs(Future(1), Future("a")), 1.second))
  println("----------------------------------")
}
