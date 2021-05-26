package main.scala.com.afecioru.ch10

object LayoutApp extends App {
  val el1 = Element("andrei")
  println(el1)

  val el2 = Element("hello", "world")
  println(el2)

  val el3 = Element('-', 10, 3)
  println(el3)

  val el4 = Element('*', 5, 3)
  println(el3 leftOf el4)
  println(el3 rightOf el4)
  println(el4 widen 10)

  println()

  println(el4 heighten 10)

  println("\n\n========================\n\n")

  val spiral = Spiral(0)
  println(spiral)


}
