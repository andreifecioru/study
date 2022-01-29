package org.afecioru.study
package ch_05
package ex_5_4_4_3


sealed trait Maybe[T] {
  def value: T
}
final case class Full[T](value: T) extends Maybe[T]
final case class Empty[T]() extends Maybe[T] {
  override def value: T = ???
}

class Ex_5_4_4_3 extends BaseTestSuite {

  test("negate elements in list") {
    List(1, 2, 3).flatMap(x => List(x, -x)) mustBe List(1, -1, 2, -2, 3, -3)
  }

  test("odd elements") {
    val result = List(Full(3), Full(2), Full(1)).map {
      case Full(value) if value % 2 == 0 => Full(value)
      case _ => Empty[Int]()
    }

    result mustBe List(Empty(), Full(2), Empty())
  }

}
