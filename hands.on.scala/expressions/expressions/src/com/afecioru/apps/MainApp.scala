package com.afecioru.apps


object MainApp extends App {
  implicit val vars = Map("x" -> 2, "y" -> 3)

  val expr = BiOp(BiOp(Variable("x"), "+", Literal(1)), "*", Variable("y"))
  println(s"$expr = ${expr.evaluate}")

  val expr1 = BiOp(BiOp(Literal(1), "+", Literal(1)), "*", Variable("y"))
  println(s"$expr1 = ${expr1.simplify}")

}