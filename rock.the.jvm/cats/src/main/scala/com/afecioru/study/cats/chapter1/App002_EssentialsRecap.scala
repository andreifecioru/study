package com.afecioru.study.cats.chapter1

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


object App002_EssentialsRecap extends App {

  // values
  val aBoolean: Boolean = false

  // expressions are EVALUATED to a value
  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // instructions vs. expression
  val theUnit = println("Hello, Scala!") // Unit is equiv of void

  // OOP
  class Animal
  class Cat extends Animal

  trait Carnivore { // equiv. of interfaces
    def eat(animal: Animal): Unit
  }

  // inheritance model: extend 1 class and 0 or more traits
  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("Crunch!")
  }

  // singleton
  object MySingleton // its a value and a type (the single value of its type)

  // companions
  object Carnivore // companion object of trait Carnivore

  // generics
  class MyList[A]

  // method notation
  val three = 1 + 2 // infix method notation
  val anotherThree = 1.+(2)

  // functional programming
  val incrementer: Int => Int = x => x + 1 // function values
  val incremented = incrementer(45) // 46

  // HOFs: map, flatMap, filter
  val processedList = List(1, 2, 3).map(incrementer) // List(2, 3, 4)
  val aLongerList = List(1, 2, 3).flatMap(x => List(x, x + 1)) // List(1,2, 2,3, 3,4)

  // for-comprehensions
  val checkerboard = List(1, 2, 3).flatMap { num =>
    List('a', 'b', 'c').map(chr => num -> chr)
  }
  val anotherBoard = for {
    num <- List(1, 2, 3)
    chr <- List('a', 'b', 'c')
  } yield num -> chr

  // options and try
  val anOption: Option[Int] = Option(/* something that might be null*/ 3) // Some(3)
  val doubledOption: Option[Int] = anOption.map(_ * 2) // Some(6)

  val anAttempt: Try[Int] = Try(/* something that might throw*/ 42) // Success(42)
  val aModifiedAttempt: Try[Int] = anAttempt.map(_ + 10) // Success(52)

  // pattern matching
  val unknown: Any = 45

  val ordinal = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  val optionDescription = anOption match {
    case Some(value) => s"the option is not empty: $value"
    case None => "the option is not empty"
  }

  // Futures
  // needs and execution context
  // import scala.concurrent.ExecutionContext.Implicits.global
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(
    Executors.newFixedThreadPool(8)
  )
  val aFuture = Future {
    // code executed in the thread pool created above
    42
  }

  // wait for completion
  aFuture.onComplete { // this is also computed on another thread
    case Success(value) => println(s"The result is: $value")
    case Failure(err) => println(s"The operation failed: ${err.getMessage}")
  }

  // map a Future
  val anotherFuture = aFuture.map(_ + 1) // Future(43) when it completes


  // partial functions: not defined for all the domain values
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 56
    case 100 => 999
  }

  // some more adv. bits
  trait HigherKindedType[F[_]]
  trait SequenceChecker[F[_]] {
    def isSequential: Boolean
  }

  val listChecker = new SequenceChecker[List] {
    override def isSequential: Boolean = true
  }
}
