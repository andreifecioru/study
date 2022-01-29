package org.afecioru.study
package ch_04
package ex_4_2_2_2

sealed trait Color {
  def red: Double
  def green: Double
  def blue: Double

  def isLight: Boolean = (red + green + blue) / 3 > 0.5
  def isDark: Boolean = !isLight
}

object Color {
  def apply(r: Double, g: Double, b: Double): Color =
    new Color {
      override val red: Double = r
      override val green: Double = g
      override val blue: Double = b
    }
}

object Colors {
  val Red: Color = Color(1.0, 0.0, 0.0)
  val Green: Color = Color(0.0, 1.0, 0.0)
  val Blue: Color = Color(0.0, 0.0, 1.0)
  val Yellow: Color = Color(0.0, 1.0, 1.0)
  val Pink: Color = Color(1.0, 0.5, 0.5)
}

sealed trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
  def color: Color
}

sealed trait Rectangular extends Shape {
  def width: Double
  def height: Double

  override val sides: Int = 4
  override def perimeter: Double = (width + height) * 2
  override def area: Double = width * height
}

final case class Circle(radius: Double, color: Color) extends Shape {
  override val sides: Int = 0
  override val perimeter: Double = 2 * math.Pi * radius
  override val area: Double = math.Pi * radius * radius
}

final case class Square(sideLength: Double, color: Color) extends Rectangular {
  override val width: Double = sideLength
  override val height: Double = sideLength
}

final case class Rectangle(width: Double, height: Double, color: Color) extends Rectangular

object Draw {
  def apply(shape: Shape): String = {
    import Colors._
    def colorDescription(color: Color): String = color match {
      case Red => "red"
      case Green => "green"
      case Yellow => "yellow"
      case Blue => "blue"
      case Pink => "pink"
      case _ if color.isLight => "light"
      case _ => "dark"
    }

    shape match {
      case Circle(radius, color) => s"A ${colorDescription(color)} circle with a radius of ${radius}cm"
      case Square(sideLength, color) => s"A ${colorDescription(color)} square with a side length of ${sideLength}cm"
      case Rectangle(width, height, color) => s"A ${colorDescription(color)} rectangle with height of ${height}cm and width of ${width}cm"
    }
  }
}


class Ex_4_2_2_2 extends BaseTestSuite {
  import Colors._

  val Black: Color = Color(0.0, 0.0, 0.0)
  val White:Color = Color(1.0, 1.0, 1.0)

  test("Draw") {
    Draw(Circle(10, Yellow)) mustBe "A yellow circle with a radius of 10.0cm"
    Draw(Square(20, Black)) mustBe s"A dark square with a side length of 20.0cm"
    Draw(Rectangle(10, 20, White)) mustBe s"A light rectangle with height of 20.0cm and width of 10.0cm"
  }

}
