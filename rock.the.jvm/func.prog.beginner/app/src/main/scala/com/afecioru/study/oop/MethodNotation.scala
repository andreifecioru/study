package com.afecioru.study.oop

import scala.language.postfixOps



object MethodNotation extends App {

  class Person(val name: String, val favouriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favouriteMovie

    def learns(language: String): String = s"$name learns $language"

    def learnsScala: String = {
      // NOTE: the infix notation requires the presence of the receiver object (this in our case)
      this learns "Scala"
    }

    // "operators" - are actually methods

    // binary operators (infix notation)
    // NOTE: method signature requires exactly one input param
    def +(other: Person): String = s"$name works with ${other.name}"

    def +(nickname: String): Person = new Person(s"$name ($nickname)", favouriteMovie, age)

    // unary operators (prefix notation)
    // NOTES:
    //   - only works with -, +, ~ and !)
    //   - no input params
    //   - note the space between the operator name and the colon (`:`)
    def unary_! : String = s"$name is shouting!"

    def unary_+ : Person = new Person(name, favouriteMovie, age + 1)

    // postfix notation
    // NOTES:
    //   - method needs to be w/o parameters to be used in postfix notation
    //   - rarely used in practice: support must be explicitly included via `import scala.language.postfixOps`
    def isAlive: Boolean = true

    // make objects (i.e. class instances) callable
    def apply(greeting: String): String = s"$greeting, my name is $name"

    def apply(times: Int): String = s"$name watched $favouriteMovie $times times."

    override def toString: String = s"Person($name)"
  }

  val andrei = new Person("Andrei", "Inception")
  val radu = new Person("Radu", "Top Gun")

  println(andrei + radu)
  println(!radu)
  println(andrei isAlive)

  println(radu("Hi"))

  println(radu + "the patient")

  val olderRadu = +radu
  println(olderRadu.age)
  println(radu learnsScala)
  println(andrei(10))

}
