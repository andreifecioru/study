package com.afecioru.apps

import com.afecioru.components._

object ExpressionApp extends App {
  def show(e: Expression): Unit = println(s"${fmt.format(e)}\n\n")

  val fmt = new ExpressionFormatter

  val e1 = BiOp("*", BiOp("/", Number(1), Number(2)),
                     BiOp("+", Var("x"), Number(1)))
  show(e1)

  val e2 = BiOp("+", BiOp("/", Var("x"), Number(2)),
                     BiOp("/", Number(1.5), Var("x")))
  show(e2)

  show(BiOp("/", e1, e2))
}
