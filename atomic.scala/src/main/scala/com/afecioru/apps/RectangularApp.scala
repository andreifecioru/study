package com.afecioru.apps

case class Point(x: Int, y: Int)

trait Rectangular {
  // invariants
  require(topLeft.x <= bottomRight.x)
  require(topLeft.y <= bottomRight.y)

  // the "thin" part
  def topLeft: Point
  def bottomRight: Point

  // the "rich" part
  def width: Int = bottomRight.x - topLeft.x
  def height: Int = bottomRight.y - topLeft.y
  def area: Int = width * height
  // ... more advance APIs (all expressed in terms of the "thin" APIs)
}

// a UI widget is a rectangular area on the screen
// we mix-in the `Rectangular` trait: provide the "thin" part
case class UIWidget(topLeft: Point, bottomRight: Point) extends Rectangular

object RectangularApp extends App {
  val widget = UIWidget(
    topLeft = Point(0, 0),
    bottomRight = Point(10, 10)
  )

  // we automatically have access to all the "rich" APIs
  println(widget.area)

}
