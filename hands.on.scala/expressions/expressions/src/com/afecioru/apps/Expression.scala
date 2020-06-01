package com.afecioru.apps

sealed trait Expression {
  def evaluate(implicit vars: Map[String, Int]): Int
  def simplify(implicit vars: Map[String, Int]): Expression = this
}

case class Variable(name: String) extends Expression {
  override def evaluate(implicit vars: Map[String, Int]): Int = vars(name)

  override def toString: String = name
}


case class Literal(value: Int) extends Expression {
  override def evaluate(implicit vars: Map[String,Int]): Int = value

  override def toString: String = value.toString
}


case class BiOp(left: Expression, op: String, right: Expression) extends Expression {
  override def evaluate(implicit vars: Map[String,Int]): Int = op match {
    case "+" => left.evaluate + right.evaluate
    case "-" => left.evaluate - right.evaluate
    case "*" => left.evaluate * right.evaluate
    case _ => throw new IllegalArgumentException(s"Unsupported operation: $op")
  }

  override def simplify(implicit vars: Map[String, Int]): Expression = (left, right) match {
    case (l: Literal, r: Literal) => Literal(evaluate)
    case _ => BiOp(left.simplify, op, right.simplify)
  }

  override def toString: String = s"($left $op $right)"
}

object Expression {
  def evaluate(expr: Expression)(implicit vars: Map[String, Int]): Int = expr.evaluate
}