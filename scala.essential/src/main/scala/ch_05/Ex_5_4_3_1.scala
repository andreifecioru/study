package org.afecioru.study
package ch_05
package ex_5_4_3_1

sealed trait Sum[T, U] {
  final def fold[V](leftFold: T => V, rightFold: U => V): V = this match {
    case Left(value) => leftFold(value)
    case Right(value) => rightFold(value)
  }
}
final case class Left[T, U](value: T) extends Sum[T,U]
final case class Right[T, U](value: U) extends Sum[T,U]

class Ex_5_4_3_1 extends BaseTestSuite {

  test("generic sum type") {
    Left[Int, String](1).value mustBe 1
    Right[Int, String]("foo").value mustBe "foo"
  }

  test("fold works") {
    Left[Int, String](1).fold(_ * 2, _.length) mustBe 2
    Right[Int, String]("foo").fold(_ * 2, _.length) mustBe 3
  }
}
