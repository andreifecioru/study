package main.scala.com.afecioru.ch6

object RationalOperations extends App {
  val r1 = Rational(4, 6)
  val r2 = Rational(2)
  val r3 = Rational(3, 5)

  println(r1 + r2)
  println(-r1)
  println(r1 / r2)
  println(r1 * r3)

  println(4 + r1)
}
