package org.afecioru.study
package ch_04
package ex_4_5_6_2

sealed trait Calculation
final case class Success(result: Int) extends Calculation
final case class Failure(reason: String) extends Calculation

object Calculator {
  def +(calculation: Calculation, value: Int): Calculation = {
    calculation match {
      case Success(v) => Success(v + value)
      case failure: Failure => failure
    }
  }

  def -(calculation: Calculation, value: Int): Calculation = {
    calculation match {
      case Success(v) => Success(v - value)
      case failure: Failure => failure
    }
  }
}


class Ex_4_5_6_2 extends BaseTestSuite {
  test ("+/- yield expected results") {
    Calculator.+(Success(1), 1) mustBe Success(2)
    Calculator.-(Success(1), 1) mustBe Success(0)
    Calculator.+(Failure("Badness"), 1) mustBe Failure("Badness")
  }

}
