package com.afecioru.study.pattmatch

import com.afecioru.study.fp.HOFsExercises.{Empty, MyList, Node, listOfValues}

import scala.util.Random

object PatternMatching extends App {

  // Pattern matching: switch on steroids
  val random = new Random()

  val randVal = random.nextInt(10)

  val description = randVal match {
    case 1 => "The one"
    case 2 => "Double or nothing"
    case 3 => "Three times is a charm"
    case _ => "something else"  // the default case
  }

  println(s"$randVal: $description")

  // Extractors with pattern matching

  // This works especially with case classes whose companion object implement the `unapply()` method
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  // pattern matching as part of assignments
  val Person(name, age) = bob
  val s"$product $price" = "Bread $30"  // pattern match against tail
  val Node(head, Node(head1, tail)) = MyList.fromValues(1, 2, 3, 4)
  println(s"Head: $head, followed by $head1, followed by $tail")


  // NOTES:
  //   - most specific branches must be listed first
  //   - if no branch matched we get a MatchError exception
  //   - the result type is the "unification" of the types returned by all branches (lowest common ancestor)
  val greeting = bob match {
    case Person(name, age) if age < 21 => s"Hi, my name is $name and I can't drink in US."
    case Person(name, age) => s"Hi, my name is $name and I am $age years old"
    case _ => "I don't know who I am."
  }

  // on sealed hierarchies:
  //   - we don't need a default case (if we provide exhaustive branches)
  //   - the compiler will also warn you if you don't cover all the branches
  sealed trait Message
  case object SuccessMessage extends Message
  case object ErrorMessage extends Message

  val message: Message = SuccessMessage

  message match { // no need to provide a `case _` branch
    case SuccessMessage => "success"
    case ErrorMessage => "error"
  }

  println(greeting)

  // matching sequences/collections
  val aList = List(1, 2, 3, 4)
  aList match {
    case List(1, _, _, _) => // extractor (there is an `unapply` implementation in the comp obj for List)
    case List(1, _*) => // extractor with var-args
    case 1 :: List(_) =>       // infix pattern
    case List(1, 2, 3) :+ 4 => // infix pattern
  }

  // type specifier -> match against types (not just values)
  val unknown: Any = 2
  unknown match {
    case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // name binding
  val myList = MyList.fromValues(1, 2, 3, 4)
  myList match {
    case _list @ Node(1, Node(2, _)) => // _list is bound to the matched value
    case Node(1, _list @ Node(2, _)) => // name binding also works on nested patterns
  }

  // multi-patterns
  myList match {
    case Empty | Node(1, _) => // compound pattern (multi-pattern)
  }

  // you cannot match against parameterized types due to type-erasure
  val result = myList match {
    case listOfStrings: MyList[String] => "list of strings" // this branch is selected because the type info is erased
    case listOfNums: MyList[Int] => "list of numbers"
    case _ => "something else"
  }
  println(result)

  // central to the pattern matching machinery is the "partial function" type
  // a partial function is a function that is not defined for all the values in its domain
  val pFunc: PartialFunction[Int, String] = {  // this is a partial function literal
    case x: Int if x % 2 == 0 => "even"
    case _ => "odd"
  }
  println(aList.map(pFunc))

  // we can use pattern matching inside for-comps.
  val listOfPersons = List(
    Person("Andrei", 40),
    Person("Radu", 25),
    Person("Cristi", 10)
  )

  for {
    Person(name, age) <- listOfPersons
  } println(s"$name is $age years old.")
}
