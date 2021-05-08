package com.afecioru.study.oop

object AnonClasses extends App {

  // start with an abstract type (trait or abstract class)
  trait Animal {
    def eat: String
  }

  // anonymous instantiation of the Animal abstract type
  val cat = new Animal {
    override def eat: String = s"Cat is eating"
  }

  println(cat.getClass) // compiler creates a synthetic type for you `com.afecioru.study.oop.AnonClasses$$anon$1`
  println(cat.eat)

  // you can anonymously extend concrete types
  class Person(val name: String) {
    def greet(): Unit = println(s"Hi! I am $name")
  }

  val andrei = new Person("Andrei") { // customize on-the-spot
    override def greet(): Unit = println(s"Hello there! My name is $name")
  }

  andrei.greet()

}
