package com.afecioru.study.fp

object Monads extends App {

  // Lists: sequences optimized for pre-pending
  val list = List(1, 2, 3)
  println(list)
  list.map(_ + 1).foreach(print)
  println

  println(list.head)
  println(list.tail)

  val secondListElement = list(1) // indexing via the `apply()` implementation (don's use [] for indexing)
  println(secondListElement)

  println(list.mkString(", "))

  println(list.filter(_% 2 == 0))

  val createSubList = (x: Int) => {
    val times10 = x * 10
    List(times10, times10 + 1, times10 + 2)
  }
  println(list.flatMap(createSubList))

  println("\n\n-----------------------------\n\n")

  // cartesian product between 2 lists
  val list1 = (1 to 5).toList
  val list2 = List(10, 20, 30)

  val cartProd = list1.flatMap(x => list2.map(y => x -> y))
  println(cartProd.mkString(", "))

  // ... same with for-comprehensions
  val cartProd2 = for {
    x <- list1 if x % 2 == 0 // guards (translated into filter calls)
    y <- list2
  } yield x -> y
  println(cartProd2.mkString(", "))

  // foreach with for comprehensions (omit yield)
  for {
    x <- list1
  } println(x)

  println("\n\n-----------------------------\n\n")

  object Maybe {
    def no[T]: Maybe[T] = No
  }

  sealed trait Maybe[+T] {
    def get: T

    def map[U](f: T => U): Maybe[U]

    def flatMap[U](f: T => Maybe[U]): Maybe[U]

    def filter(p: T => Boolean): Maybe[T]

    def foreach(action: T => Unit): Unit
  }

  case object No extends Maybe[Nothing] {
    def get: Nothing = throw new NoSuchElementException

    def map[U](f: Nothing => U): Maybe[U] = Maybe.no[U]

    override def flatMap[U](f: Nothing => Maybe[U]): Maybe[U] = Maybe.no[U]

    override def filter(p: Nothing => Boolean): Maybe[Nothing] = Maybe.no[Nothing]

    override def foreach(action: Nothing => Unit): Unit = ()
  }

  case class Yes[+T](private val value: T) extends Maybe[T] {
    override def get: T = value

    override def map[U](f: T => U): Maybe[U] = Yes(f(value))

    override def flatMap[U](f: T => Maybe[U]): Maybe[U] = f(value)

    override def filter(p: T => Boolean): Maybe[T] =
      if (p(value)) this
      else Maybe.no[T]

    override def foreach(action: T => Unit): Unit = action(value)
  }

  val yes = Yes(10)
  yes.foreach(println)
  println(yes.map(_ * 2))
  println(yes.flatMap(x => Yes(x * 3)))
  println(yes.filter(_ > 20))
}
