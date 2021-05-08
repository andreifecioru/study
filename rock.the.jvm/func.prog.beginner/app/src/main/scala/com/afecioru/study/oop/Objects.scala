package com.afecioru.study.oop

object Objects extends App {
  /** Objects in Scala allow the implementation of class-level functionality (like static members in Java)
   *   - they define both a type and a singleton
   *   - can act as "companions" of classes with the same name.
   *      - companion obj can access private members in corresponding companion classes and vice-versa
   *      - with the use of apply() they can act as factories for the corresponding companion classes

   * Scala has no support for "static" definitions in classes. We use companion object for that
   */

  object Person { // companion obj for Person class
    val N_EYES: Int = 2

    private val SECRET: String = "secret"

    def canFly: Boolean = false

    // factory methods
    def from(firstName: String, lastname: String): Person = new Person(s"$firstName $lastname", 0)

    // make the companion object callable
    def apply(name: String, age: Int): Person = new Person(name, age)
  }

  class Person(val name: String, val age: Int) { // companion
    def greet: String = s"Hi, my name is $name and I am $age years old"

    // companion classes can access private members in the companion objects
    def secret: String = s"I know a secret: ${Person.SECRET}"

  }

  println(Person.N_EYES)
  println(Person.canFly)

  // because we have an `apply()` factory method in the companion object, we can instantiate w/o the new keyword
  val andrei = Person("Andrei", 40)

  // NOTE: Scala apps are object definitions with a method:
  //    def main(args: Array[String]): Unit
  // the App trait defines the `main` method for you

}
