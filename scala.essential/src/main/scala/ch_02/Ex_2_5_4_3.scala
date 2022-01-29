package org.afecioru.study
package ch_02

object Calc2 {
  def square(value: Double): Double = value * value
  def cube(value: Double): Double = square(value) * value

  def square(value: Int): Int = value * value
  def cube(value: Int): Int = square(value) * value
}

class Ex_2_5_4_3 extends BaseTestSuite {

  test("int-based APIs yield correct result") {
    val resultSquare = Calc2.square(2)
    val resultCube = Calc2.cube(-3)

    resultSquare mustBe 4
    resultSquare mustBe a[Int]

    resultCube mustBe -27
    resultCube mustBe a[Int]
  }

}
