package com.afecioru.study.cats.chapter1

object App003_ImplicitsRecap extends App {

  // implicit classes (one arg wrappers over values)
  case class Person(name: String) {
    def greet: String = s"Hi, my name is $name!"
  }

  implicit class ImpersonatorString(val name: String) {
    def greet: String = Person(name).greet
  }
  "Andrei".greet // extension method 'greet' available on String class

  // importing implicit conversions in scope
  import scala.concurrent.duration._
  val oneSec = 1.second

  // implicit args and vals
  def increment(x: Int)(implicit amount: Int): Int = x +amount

  implicit val defaultAmount = 10
  val incremented = increment(2) // 10 is passed implicitly by the compiler

  def multiply(x: Int)(implicit times: Int): Int = x * times
  val timesTwo = multiply(2)

  // more complex example
  trait JsonSerializer[T] {
    def toJson(value: T): String
  }

  def listToJson[T](list: List[T])
                   (implicit serializer: JsonSerializer[T]): String = {
    list.map(serializer.toJson).mkString("[", ", ", "]")
  }

  implicit val personSerializer: JsonSerializer[Person] = (person: Person) => {
    s"""
       |{"name" : "${person.name}"}
       |""".stripMargin.trim
  }

  val personsJson = listToJson(List(
    Person("Alice"), Person("Bob")
  ))

  println(personsJson)

  // implicit arg is used to "prove" the existence of a type

  // implicit methods
  implicit def oneArgCaseClassSerializer[T <: Product]: JsonSerializer[T] = (value: T) =>
    s"""
       |"{${value.productElementName(0)}": "${value.productElement(0)}"}
       |""".stripMargin.trim


  def toJson[T](value: T)
               (implicit serializer: JsonSerializer[T]): String = {
    serializer.toJson(value)
  }
  case class Cat(name: String)

  val tom = Cat("Tom")
  println(toJson(tom))

  val catToJson = listToJson(List(
    Cat("Tom"),
    Cat("Garfield")
  ))
  println(catToJson)

  // implicit methods are used to "prove" the existence of a type

  // implicit methods can also be use for automatic type conversion
  // (not recommended - use an implicit class instead)




}
