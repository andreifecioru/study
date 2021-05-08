package com.afecioru.study.oop


object Exceptions extends App {
  // Exceptions are mainly used in Scala to interface with Java code

  // try/catch is an expression (produces a value)
  // the type of the result is a lest common ancestor of the values produced by the
  // success branch and any of the case branches
  //
  // NOTE:
  //   - finally block does not influence the result type of the try/catch expression
  //   - finally is used for side-effects (like cleaning up)
  val result = try {
    5 / 0
  } catch {
    case e: ArithmeticException =>
      println(e.getMessage)
      0
  } finally {
    println("Cleaning up...")
  }

  println(result)

  // throwing an exception is actually an expression which evaluates to Nothing
  // val nothing: Nothing = throw new NullPointerException

  /**
   * You can only throw instances of the Throwable class.
   *
   * Notable throwable classes:
   *  - Exception: something wrong with the app (like NullPointerException)
   *  - Error: something wrong with the system (like StackOverflowError)
   */
}
