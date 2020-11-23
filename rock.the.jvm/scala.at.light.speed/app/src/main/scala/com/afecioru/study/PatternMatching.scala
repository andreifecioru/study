package com.afecioru.study

object PatternMatching extends App {

  // pattern matching is like "switch" on steroids

  // match based on values
  val myValue = 55

  // just like everything else, pattern matches are expressions and produce values
  val message = myValue match {
    case 1 => "1st"
    case 2 => "2nd"
    case 3 => "3rd"
    case _ => myValue + "th" // the default case
  }

  println(s"Message: $message")

  // we can deconstruct objects (via the `unapply` method)
  case class Person(name: String, age: Int)

  val andrei = Person("Andrei", 40)

  // we can also apply conditionals in the matching expressions
  val message1 = andrei match {
    case Person(name, age) if age < 20 => s"$name is $age old. So young..."
    case Person(name, age) if age < 30 => s"$name is $age old. Still young..."
    case Person(name, age) => s"$name is $age old. Pretty old.."
  }

  // we can deconstruct tuples
  val myTuple = (10, 11)

  val message2 = myTuple match {
    case (first, second) if first < second => "Second value is bigger"
    case (first, second) if first > second => "First value is bigger"
    case _ => "Both values are equal"
  }
  println(message2)

  // we can deconstruct lists
  val myList = List(1, 2, 3)
  val message3 = myList match {
    case List(_, 2, _) => "2 is the 2nd element in the list"
    case _ :: 2 :: _ => "same as above"
    case _ :: 2 :: 3 :: nil => "2 is the 2nd element and 3 is the last"
    case _ => "Something else"
  }
  println(message3)
}
