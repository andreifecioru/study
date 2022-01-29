package org.afecioru.study
package ch_04
package ex_4_1_4_3

trait Rectangular extends Shape {
  def width: Double
  def height: Double

  override val sides: Int = 4
  override def perimeter: Double = (width + height) * 2
  override def area: Double = width * height
}

case class Square(sideLength: Double) extends Rectangular {
  override val width: Double = sideLength
  override val height: Double = sideLength
}

case class Rectangle(width: Double, height: Double) extends Rectangular


class Ex_4_1_4_3 extends BaseTestSuite {

  test("Square") {
    Square(3.0) must have (
      Symbol("sideLength") (3.0),
      Symbol("width") (3.0),
      Symbol("height") (3.0),
      Symbol("sides") (4),
      Symbol("perimeter") (12.0),
      Symbol("area") (9.0)
    )
  }

  test("Rectangle") {
    Rectangle(2.0, 4.0) must have (
      Symbol("width") (2.0),
      Symbol("height") (4.0),
      Symbol("sides") (4),
      Symbol("perimeter") (12.0),
      Symbol("area") (8.0)
    )
  }

}
