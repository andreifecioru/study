package com.afecioru.study.fp

import scala.annotation.tailrec

object HOFsCurries extends App {

  // function that applies a function n times over a given value
  def nTimes[T](func: T => T, times: Int): T => T = {

    @tailrec
    def _helper(func: T => T, times: Int, arg: T): T = {
      if (times <= 0) arg
      else _helper(func, times - 1, func(arg))
    }

    (arg: T) => _helper(func, times, arg)
  }

  val addOneSixTimes = nTimes[Int](_ + 1, 6)
  println(addOneSixTimes(10))

  val doubleThreeTimes = nTimes[Double](_ * 2, 3)
  println(doubleThreeTimes(1.2))

  // alternative implementation
  def nTimes2[T](func: T => T, times: Int): T => T =
    if (times <= 0) (x: T) => x // the identity function
    else (x: T) => nTimes2(func, times - 1)(x)

  // functions with multiple parameter list
  def greeter(greet: String)(name: String): String = s"$greet, my name is $name"

  val helloGreeter: String => String = greeter("Hello")
  println(helloGreeter("Andrei"))

  // if you don't want to specify the type,
  // append an `_` to the invocation of the curried function (partial function application)
  val hiGreeter = greeter("Hi")_
  println(hiGreeter("Radu"))
}
