package com.afecioru.study

import javax.xml.bind.ValidationException

object FunctionalProgramming extends App {

  class Person(name:String) {
    def apply(age: Int): Unit = println(s"I am $age years old")
  }

  val andrei = new Person("Andrei")
  // objects are "callable" when they implement `apply`
  andrei(40) // same as `andrei.apply(40)

  // Functional programming:
  //  - immutable values
  //  - higher-order functions (HOFs) as values
  //    - pass function as args
  //    - return functions from functions as results
  //  - compose functions
  // Scala defines a set of traits: FunctionX (Function1, Function2, ..., Function22)

  // creating function values by hand
  val incrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  println(s"10 + 1 = ${incrementer(10)}")
  println(s"10 + 1 = ${adder(10, 1)}")

  // syntactic sugar is available
  val incrementer1 = (v: Int) => v + 1        // type is Int => Int (equiv. to Function1[Int, Int])
  val adder1 = (v1: Int, v2: Int) => v1 + v2  // type is (Int, Int) => Int (equiv. to Function2[Int, Int, Int])
  println(s"11 + 1 = ${incrementer1(11)}")
  println(s"11 + 1 = ${adder1(11, 1)}")

  // super-condensed functional syntax
  List(1, 2, 3).map(_ + 1).foreach(println)

  println("----------")

  List(1, 2, 3)
    .flatMap { x =>
      List(x, 2 * x)
    }
    .foreach(println)

  println("----------")

  (1 to 10)
    .filter(_ < 5)
    .map(_ * 2)
    .foreach(println)

  println("----------")
  // cartesian product between [1, 2, 3] and ['a', 'b', 'c']
  (1 to 3)
    .flatMap { num =>
      ('a' to 'c').map(num -> _)
    }
    .foreach(println)

  println("----------")
  // same, but with for-comps
  (for {
    num <- 1 to 3
    letter <- 'a' to 'c'
  } yield (num, letter)).foreach(println)

  // Collections
  // lists:
  val myList = List(1, 2, 4)
  val first = myList.head
  val rest = myList.tail
  val prepended = 0 :: myList // prepend
  val added = 0 +: myList :+ 6 // prepend and append

  // sequences
  val mySeq = Seq(1, 2, 3)
  // you can access an element at an index
  val element = mySeq(1) // calls `.apply` - do not use [] for indexing!

  // vectors: fast sequence implementations
  val myVector = Vector(1, 2, 3)

  // sets: collection with no duplicates
  val mySet = Set(1, 2, 3, 3, 3) // duplicates are removed: Set(1, 2, 3)
  // efficient containment checks
  mySeq.contains(2) // true
  // add elements
  val addedSet = mySet + 5    // Set(1, 2, 3, 5)
  val removedSet = mySet - 2  // Set(1, 3)

  // ranges: values are generated lazily
  val rangeOne = 1 to 100
  val rangeTwo = 1 to 100 by 2
  // force the generation of all values in a range
  val rangeValues = rangeOne.toList

  // tuples - groups of values just like in Python
  val myTuple = ("Andrei", "Fecioru")
  val myTuple1 = "Andrei" -> "Fecioru"  // same as above (used to construct maps)

  // maps - same as dictionaries
  val persons = Map(
    "Andrei" -> "Fecioru",
    "Ion" -> "Popescu"
  )
}

