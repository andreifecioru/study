package com.afecioru.study.oop

import scala.annotation.tailrec

// NOTE: by default constructor params are translated into *private* fields.
class Person0(name: String, age: Int) // constructor w/ params

// adding val/var to the constructor params, translates them into public fields
class Person(val name: String, val age: Int) {
  // code here represents the constructor body (executed at construction time)
  println("Constructing...")

  // fields
  val gender = "male"

  // methods
  def sayHi(): Unit = println(s"Hi, my name is $name")

  // overloading
  def sayHi(greeting: String): Unit = println(greeting)

  // auxiliary constructors
  def this(name: String) = this(name, 0) // implementation has to be a call to another constructor (and nothing else)
}

class Writer(val firstName: String, val lastName: String, val yearOfBirth: Int) {
  lazy val fullName: String = s"$firstName $lastName"
}

class Novel(val title: String, val releaseYear: Int, val author: Writer) {
  lazy val authorAge: Int = releaseYear - author.yearOfBirth

  def isWrittenBy(author: Writer): Boolean = author.fullName == this.author.fullName

  def copy(releaseYear: Int): Novel = new Novel(this.title, releaseYear, this.author)
}

class Counter(val value: Int) {
  def increment(): Counter = {
    println("Incrementing...")
    new Counter(value + 1)
  }
  def decrement(): Counter = new Counter(value - 1)

  @tailrec
  final def increment(amount: Int): Counter = {
    if (amount <= 0) this
    else increment().increment(amount - 1)
  }

  def decrement(amount: Int): Counter = new Counter(value - amount)
}

object OopBasics extends App {

  val andrei = new Person("Andrei", 40)
  println(s"Name is ${andrei.name}")
  andrei.sayHi()

  val counter = new Counter(10)
  counter.increment(5)

}
