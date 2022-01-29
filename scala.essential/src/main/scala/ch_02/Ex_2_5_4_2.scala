package org.afecioru.study
package ch_02

object Calc {
  def square(value: Double): Double = value * value
  def cube(value: Double): Double = square(value) * value
}

class Ex_2_5_4_2 extends BaseTestSuite {

  test("square yields the correct result") {
    assert(Calc.square(2) === 4.0)
    assert(Calc.square(-3) === 9.0)
  }

  test("cube yields the correct result") {
    assert(Calc.cube(2) === 8.0)
    assert(Calc.cube(-3) === -27.0)
  }

}
