package org.afecioru.study
package ch_05
package ex_5_4_4_1

sealed trait Maybe[T] {
  def value: T

  final def fold[U](zero: U)(f: T => U): U = this match {
    case Empty() => zero
    case Full(value) => f(value)
  }
}
final case class Full[T](value: T) extends Maybe[T]
final case class Empty[T]() extends Maybe[T] {
  override def value: T = ???
}

class Ex_5_4_4_1 extends BaseTestSuite {

  test("generic optional type") {
    Full[Int](1).value mustBe 1
    an [NotImplementedError] mustBe thrownBy(Empty[Int]().value)
  }

  test ("fold works") {
    val res1 = Full[String]("andrei").fold(0)(_.length)
    res1 mustBe 6

    val res2 = Empty[String]().fold(0)(_.length)
    res2 mustBe 0
  }
}
