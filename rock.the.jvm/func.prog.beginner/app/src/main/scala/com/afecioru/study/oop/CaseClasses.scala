package com.afecioru.study.oop

object CaseClasses extends App {

  /**
   * In Scala, case classed are light-weight data holder objects with some code automatically
   * generated by the compiler:
   *   - constructor params are promoted to fields
   *   - companion object with factory method definition
   *   - toString implementation
   *   - equals and hash implementation
   *   - serialization support
   *   - a copy() method
   *   - unapply() implementation for pattern matching support (i.e. default extractors)
   */

  case class Person(name: String, age: Int)

  val andrei = Person("Andrei", 40)
  val andrei2 = Person("Andrei", 40)

  println(andrei)
  println(andrei.name)

  println(andrei == andrei2)

  val andrei3 = andrei.copy(age = 45)
  println(andrei3)

  // case objects => same as case classes but singletons
  // NOTE: objects can't receive constructor params
  case object MyCaseObject {
    val description: String = "A case object."
  }
}
