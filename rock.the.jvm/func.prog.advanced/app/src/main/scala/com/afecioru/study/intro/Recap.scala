package com.afecioru.study.intro

import scala.annotation.tailrec

object Recap extends App {

  // val vs. var
  val aCondition: Boolean = false
  var aNumber = 0  // compiler can infer types
  aNumber = 42

  // statements vs. expressions

  // if are expressions (like almost everything in Scala)
  val aConditionalVal = if (aCondition) 42 else 65

  // code blocks (also expressions)
  val aCodeBlock = {
    val aNumber = 44

    // code-blocks evaluate to the value of the *last* expression
    aNumber
  }

  // The Unit type: for expressions that produce side-effects
  val aUnit: Unit = println("Hello")
  val theOnlyUnit: Unit = () // () is the only value of type Unit (a singleton)

  // functions
  def aFunction(x: Int): Int = x + 1

  // recursion (stack vs. tail recursion)
  @tailrec
  def factorial(n:Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else factorial(n - 1, acc *  n)

  println(factorial(5))

  // OOP basics
  class Animal
  class Dog extends Animal  // single-class inheritance

  val aDog: Animal = new Dog  // OOP polymorphism by sub-typing

  // abstract types (abstract classes and traits)

  trait Carnivore {
    def hunt(animal: Animal): Unit
  }

  // extends a single class, but can mixin multiple traits
  class Crocodile extends Animal with Carnivore {
    override def hunt(animal: Animal): Unit = println("Eating an animal")
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.hunt(aDog)
  aCroc hunt aDog  // infix notation

  // operators are actually methods
  1 + 2
  1.+(2)

  // anonymous classes
  val aCarnivore = new Carnivore { // provide implementation for the abstract members
    override def hunt(animal: Animal): Unit = println("Just eating.")
  }

  // generics (with declare-site variance options)
  abstract class MyList[+T] // covariant type

  // singletons and companion objects
  object MyList // companion object for the MyList trait

  // case classes - light-weight data structures with lots of out-of-the-box functionality
  case class Person(name: String, age: Int)

  // exception handling
  // val throwsException: Nothing = throw new RuntimeException  // Nothing is the most-bottom type
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I got an exception"
  } finally { // finally is for side-effect
    println("Some logs")
  }

  // functional programming
  // the presence of the `apply()` method transforms an instance / object into callable instances

  // function values are supported under the hood by the FunctionX[T1...] family of traits
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  println(incrementer(1))

  // syntactic sugar for the above
  val incrementer1 = (x: Int) => x + 1

  // HOFs
  println(List(1, 2, 3).map(incrementer1).mkString(", "))

  // map, flatMap, filter and for-comps
  val result = for {
    n1 <- 1 to 10 if n1 % 2 == 0
    n2 <- 100 to 110
  } yield n1 -> n2
  println(result)

  // ... the above is the same as
  val result1 = (1 to 10)
    .filter(_ % 2 == 0)
    .flatMap { n1 =>
      (100 to 110).map { n2 => n1 -> n2}
    }
  println(result1)

  // Scala collections: Seq, Array, List, Vector, Map, Tuples, Range
  val aMap = Map(
    "Andrei" -> 10,
    ("Radu", 20),
  )

  // sum-types: Option, Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "1st"
    case 2 => "2nd"
    case 3 => "3rd"
    case _ => s"${x}th"
  }

  val bob = Person("Bob", 22)

  // extractors via the `unapply()` method
  val greeting = bob match {
    case Person(name, age) => s"Hi, my name is $name and I am $age years old."
  }
  println(greeting)
}
