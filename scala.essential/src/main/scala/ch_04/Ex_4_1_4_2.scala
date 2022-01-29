package org.afecioru.study
package ch_04

trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
}

case class Circle(radius: Double) extends Shape {
  override val sides: Int = 0
  override val perimeter: Double = 2 * math.Pi * radius
  override val area: Double = math.Pi * radius * radius
}

case class Square(sideLength: Double) extends Shape {
  override val sides: Int = 4
  override val perimeter: Double = sides * sideLength
  override val area: Double = sideLength * sideLength
}

case class Rectangle(width: Double, height: Double) extends Shape {
  override val sides: Int = 4
  override val perimeter: Double = (width + height) * 2
  override val area: Double = width * height
}

class Ex_4_1_4_2 extends BaseTestSuite {

  test("Circle") {
    Circle(4.0) must have (
      Symbol("radius") (4.0),
      Symbol("sides") (0),
      Symbol("perimeter") (8 * math.Pi),
      Symbol("area") (16 * math.Pi)
    )
  }

  test("Square") {
    Square(3.0) must have (
      Symbol("sideLength") (3.0),
      Symbol("sides") (4),
      Symbol("perimeter") (12.0),
      Symbol("area") (9.0)
    )
  }

  test("Rectangle") {
    Rectangle(3.0, 4.0) must have (
      Symbol("width") (3.0),
      Symbol("height") (4.0),
      Symbol("sides") (4),
      Symbol("perimeter") (14.0),
      Symbol("area") (12.0)
    )
  }

}
