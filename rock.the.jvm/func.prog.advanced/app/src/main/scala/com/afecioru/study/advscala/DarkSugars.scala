package com.afecioru.study.advscala

object DarkSugars extends App {


  // #1: calling methods with a single param
  def greet(name: String): Unit = println(s"Hello, $name!")

  greet { // pass a block which evaluates to the expected param
    println("Preparing the greeting...")
    "andrei"
  }

  println("\n------------------------------------------------\n")


  // #2: Single Abstract Method (SAM) pattern
  // Start with an abstract type with a single abstract method (can also have other non-abstract methods)
  abstract class Operation {
    def compute(op1: Int, op2: Int): Int = {
      println("Computing...")
      operation(op1, op2)
    }
    def operation(x: Int, y: Int): Int
  }

  // we can assign to an instance of a SAM a lambda that matches the signature of the abstract method in the SAM
  val add: Operation = (x, y) => x + y
  println(add.compute(10, 20))

  val mul: Operation = _ * _  // even shorter
  println(mul.compute(2, 3))

  println("\n------------------------------------------------\n")


  // #3: Operators ending with ':' are right associative
  //  - operators are regular methods in Scala.
  //  - if the method name ends in ':', it will be applied to the right operand (when used in infix notation)
  class StringChain(chain: String = "|") {
    def -->:(value: String): StringChain = new StringChain(s"$value-$chain")

    override def toString: String = chain
  }

  println("one" -->: "two" -->: "three" -->: new StringChain)

  println("\n------------------------------------------------\n")

  // #6: Arbitrary method names
  // Methods in Scala can have arbitrary names
  case class Teen(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }

  val lilly = Teen("Lilly")
  lilly `and then said` "'cool outfit!'"

  println("\n------------------------------------------------\n")

  // #7: Infix generic types
  // When you have a generic type with 2 type params we can use the infix notation for the types.
  trait <[T, U]
  val lessThan: Int < String = null

  println("\n------------------------------------------------\n")

  // #8: Setter member syntax for mutable objects
  class WrappedInt(var num: Int) {
    def value: Int = num
    def value_=(num: Int): Unit = this.num = num  // setter syntax

    override def toString: String = s"WrappedInt($value)"
  }

  val myInt = new WrappedInt(10)
  println(myInt)

  myInt.value = 100
  println(myInt)

  println("\n------------------------------------------------\n")

  // #9: The update() methods for mutable containers
  class MutableBox[T](private[this] var value: T) {
    override def toString: String = s"MutableBox($value)"

    def update(newValue: T): Unit = value = newValue
  }

  val mutableInt = new MutableBox[Int](0)
  println(mutableInt)
  mutableInt.update(100)
  println(mutableInt)

}
