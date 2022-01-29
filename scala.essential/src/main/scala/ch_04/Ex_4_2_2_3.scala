package org.afecioru.study
package ch_04

sealed trait DivisionResult

final case class Finite(value: Int) extends DivisionResult
object Infinite extends DivisionResult

object divide {
  def apply(op1: Int, op2: Int): DivisionResult =
    if (op2 != 0) Finite(op1 / op2) else Infinite
}


class Ex_4_2_2_3 extends BaseTestSuite {
  test("divide API") {
    divide(3, 2) mustBe Finite(1)
    divide(1, 0) mustBe Infinite
  }
}
