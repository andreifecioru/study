package com.afecioru.study.basics

object Expressions extends App {
  // almost everything in Scala is an expression that evaluates to something

  // even expressions that perform side-effects (like `while` and assignments)
  // evaluate to () (type Unit)

  var aVariable = 42
  val aValue: Unit = (aVariable = 2)
  println(aValue) // (): Unit

  val anotherValue: Unit = while (aVariable < 10) {
    aVariable += 1
  }
  println(anotherValue)

  // there are some things that are not expressions (i.e. definitions)
}
