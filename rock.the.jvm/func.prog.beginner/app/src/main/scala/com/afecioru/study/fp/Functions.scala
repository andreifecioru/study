package com.afecioru.study.fp

object Functions extends App {

  // We want functions as 1st class elements

  // functions as values

  // the OOP way (functional interfaces => abstract trait with a single abstract method)
  trait MyFunction[-A, +B] {
    def apply(arg: A): B
  }

  val aFunction = new MyFunction[Int, String] {
    override def apply(arg: Int): String = s"-${arg.toString}-"
  }
  println(aFunction(3)) // since we implement `apply` our function object is "callable"

  // Scala already provides the FunctionX traits (i.e. Function2 ... Function22)
  val doubler = new Function[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }
  println(doubler(4))

  // function literals - LAMBDAs (i.e. function values) syntactic sugar
  val max = (x: Int, y: Int) => if (x > y) x else y // type of max is (Int, Int) => Int
  println(max(4, 10))

  val concat = (x: String, y: String) => x + y
  println(concat("Hello, " , "world!"))

  val adder = (x: Int) => (y: Int) => x + y // type of adder is `Int => Int => Int
  val multiplier: Int => Int => Int = x => y => x * y

  val addTwo = adder(2)
  println(addTwo(5))
  println(adder(3)(5)) // curried function

  // alternative syntax
  val stringToInt = { (s: String) =>
    println(s"Converting string: $s")
    s.toInt
  }
  println(stringToInt("5"))

  // more syntactic sugar
  val addTwoNumbers: (Int, Int) => Int = _ + _ // NOTE: in this case the type annotation is required
}
