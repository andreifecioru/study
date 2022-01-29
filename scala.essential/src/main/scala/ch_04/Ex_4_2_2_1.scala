package org.afecioru.study
package ch_04
package ex_4_2_2_1

sealed trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
}

sealed trait Rectangular extends Shape {
  def width: Double
  def height: Double

  override val sides: Int = 4
  override def perimeter: Double = (width + height) * 2
  override def area: Double = width * height
}

final case class Circle(radius: Double) extends Shape {
  override val sides: Int = 0
  override val perimeter: Double = 2 * math.Pi * radius
  override val area: Double = math.Pi * radius * radius
}

final case class Square(sideLength: Double) extends Rectangular {
  override val width: Double = sideLength
  override val height: Double = sideLength
}

final case class Rectangle(width: Double, height: Double) extends Rectangular

object Draw {
  def apply(shape: Shape): String = shape match {
    case Circle(radius) => s"A circle with a radius of ${radius}cm"
    case Square(sideLength) => s"A square with a side length of ${sideLength}cm"
    case Rectangle(width, height) => s"A rectangle with height of ${height}cm and width of ${width}cm"
  }
}


class Ex_4_2_2_1 extends BaseTestSuite {

  test("Draw") {
    Draw(Circle(10)) mustBe "A circle with a radius of 10.0cm"
    Draw(Square(20)) mustBe s"A square with a side length of 20.0cm"
    Draw(Rectangle(10, 20)) mustBe s"A rectangle with height of 20.0cm and width of 10.0cm"
  }

}
