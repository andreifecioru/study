package org.afecioru.study
package ch_04
package ex_4_7_0_1


sealed trait Result
final case class Success(value: Double) extends Result
final case class Failure(reason: Calculator.Error) extends Result

sealed trait Expression
final case class Number(value: Double) extends Expression
final case class Addition(op1: Expression, op2: Expression) extends Expression
final case class Subtraction(op1: Expression, op2: Expression) extends Expression
final case class Division(op1: Expression, op2: Expression) extends Expression
final case class SquareRoot(op: Expression) extends Expression


object Calculator {
  case class Error(message: String) extends AnyVal
  val DIVISION_BY_ZERO: Error = Error("Division by zero")
  val SQUARE_ROOT_NEG_NUMBER: Error = Error("Square root of negative number")

  private def composeResults(results: (Result, Result))
                            (compose: (Double, Double) => Double): Result = results match {
    case (Failure(reason_op1), Failure(reason_op2)) =>
      Failure(Error(s"${reason_op1.message} | ${reason_op2.message}"))
    case (Failure(reason), _) => Failure(reason)
    case (_, Failure(reason)) => Failure(reason)
    case (Success(val_op1), Success(val_op2)) => Success(compose(val_op1, val_op2))
  }

  def eval(expression: Expression): Result = expression match {
    case Number(value) => Success(value)
    case Addition(op1, op2) => composeResults(eval(op1) -> eval(op2))(_ + _)
    case Subtraction(op1, op2) => composeResults(eval(op1) -> eval(op2))(_ - _)
    case Division(op1, op2) =>
      eval(op2) match {
        case Success(op2_val) if op2_val == 0 =>
          composeResults(eval(op1), Failure(DIVISION_BY_ZERO))(_ / _)
        case op2_result =>
          composeResults(eval(op1), op2_result)(_ / _)
      }
    case SquareRoot(op) => eval(op) match {
      case Success(op_val) if op_val < 0 => Failure(SQUARE_ROOT_NEG_NUMBER)
      case Success(op_val) => Success(Math.sqrt(op_val))
      case result => result
    }
  }
}

class Ex_4_7_0_1 extends BaseTestSuite {
  import Calculator._

  test("calculator works") {
    eval(Addition(SquareRoot(Number(-1.0)), Number(2.0))) mustBe Failure(SQUARE_ROOT_NEG_NUMBER)
    eval(Addition(SquareRoot(Number(4.0)), Number(2.0))) mustBe Success(4.0)
    eval(Division(Number(4), Number(0))) mustBe Failure(DIVISION_BY_ZERO)
  }

}
