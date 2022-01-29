package org.afecioru.study
package ch_05
package ex_5_6_6_2

import ch_05.ex_5_4_1_1.Pair

sealed trait Sum[L, +R] {
  final def map[U](f: R => U): Sum[L, U] = this match {
    case Success(value) => Success(f(value))
    case Failure(value) => Failure(value)
  }

  final def flatMap[U](f: R => Sum[L, U]): Sum[L, U] = this match {
    case Success(value) => f(value)
    case Failure(value) => Failure(value)
  }
}
case class Success[L, R](value: R) extends Sum[L, R]
case class Failure[L, R](value: L) extends Sum[L, R]

sealed trait Expression {
  final def eval: Sum[String, Double] = this match {
    case Addition(op1, op2) =>
      for (op1_val <- op1.eval; op2_val <- op2.eval) yield op1_val + op2_val
    case Subtraction(op1, op2) =>
      for (op1_val <- op1.eval; op2_val <- op2.eval) yield op1_val - op2_val
    case Division(op1, op2) =>
      (for (op1_val <- op1.eval; op2_val <- op2.eval) yield {
        op1_val -> op2_val
      }).flatMap {
        case (_, denom) if denom == 0 => Failure("Division by zero")
        case (nom, denom) => Success(nom/denom)
      }
    case SquareRoot(op) => op.eval.flatMap {
      case value if value < 0 => Failure("Square root of negative number")
      case value => Success(Math.sqrt(value))
    }
    case Number(value) => Success(value)
  }
}
final case class Addition(op1: Expression, op2: Expression) extends Expression
final case class Subtraction(op1: Expression, op2: Expression) extends Expression
final case class Division(op1: Expression, op2: Expression) extends Expression
final case class SquareRoot(op: Expression) extends Expression
final case class Number(value: Double) extends Expression


class Ex_5_6_6_2 extends BaseTestSuite {

  test("calculator works on valid expressions") {
    val expression = Addition(
      Subtraction(Number(1.0), Number(2.0)),
      SquareRoot(Number(4.0))
    )

    expression.eval mustBe Success(1.0)
  }

  test("calculator fails on division by zero") {
    val expression = Addition(
      Subtraction(Number(1.0), Number(2.0)),
      Division(Number(4.0), Number(0.0))
    )

    expression.eval mustBe Failure("Division by zero")
  }

  test("calculator fails on sq-root of neg. no.") {
    val expression = Addition(
      Subtraction(Number(1.0), Number(2.0)),
      SquareRoot(Number(-4.0))
    )

    expression.eval mustBe Failure("Square root of negative number")
  }

}
