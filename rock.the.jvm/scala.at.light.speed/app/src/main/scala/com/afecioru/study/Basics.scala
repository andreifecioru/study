package com.afecioru.study

object Basics extends App {
  val name: String = "Andrei"

  println(s"Name is $name")

  // Everything in Scala is an expression

  // if blocks are expressions
  val age = if (name == "Andrei") 40 else 20
  println(s"Andrei is $age years old")

  // code-blocks (are also expressions)
  val result = {
    // this value is only visible inside this block
    val someValue = 10

    def addOne(value: Int): Int = value + 1

    // the block is evaluated as the value of the last expressing in the block
    addOne(someValue)
  }
  print(s"Result is $result")

  // recursive functions (Scala has support for tail-call optimization)
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else n *factorial(n - 1)

  print(s"10! is: ${factorial(10)}")

  // Unit type has a single value () - equivalent to None in Python
  val noValue: Unit = ()
}
