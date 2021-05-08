package com.afecioru.study.pattmatch

object Expressions extends App {

  trait Expr

  case class Num(value: Int) extends Expr {
    override def toString: String = s"$value"
  }

  case class Sum(op1: Expr, op2: Expr) extends Expr {
    override def toString: String = s"$op1 + $op2"
  }

  case class Mul(op1: Expr, op2: Expr) extends Expr {
    override def toString: String = (op1, op2) match {
      case (_:Sum, _:Sum) => s"($op1) * ($op2)"
      case (_:Sum, _) => s"($op1) * $op2"
      case (_, _:Sum) => s"$op1 * ($op2)"
      case _ => s"$op1 * $op2"
    }
  }

  val expr1 = Mul(Sum(Num(1), Num(2)), Num(3))
  println(expr1)

  val expr2 = Mul(Sum(Num(1), Num(2)), Sum(Num(3), Num(4)))
  println(expr2)

  val expr3 = Mul(Num(1), Mul(Num(2), Num(3)))
  println(expr3)

  val expr4 = Sum(Num(1), Mul(Num(2), Num(3)))
  println(expr4)
}
