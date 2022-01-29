package org.afecioru.study
package ch_05
package ex_5_5_4_4

sealed trait Sum[T, U] {
  final def map[V](f: U => V): Sum[T, V] = this match {
    case Failure(value) => Failure(value)
    case Success(value) => Success(f(value))
  }

  final def flatMap[V](f: U => Sum[T, V]): Sum[T, V] = this match {
    case Failure(value) => Failure(value)
    case Success(value) => f(value)
  }
}

final case class Failure[T, U](value: T) extends Sum[T, U]
final case class Success[T, U](value: U) extends Sum[T, U]

class Ex_5_5_4_4 extends BaseTestSuite {
  test("map works") {
    Success[String, Int](10).map(_ * 2) mustBe Success(20)
    Failure[String, Int]("error").map(_ * 2) mustBe Failure("error")
  }

  test("flatMap works") {
    Success[String, Int](10).flatMap(value => Success(value * 2)) mustBe Success(20)
    Failure[String, Int]("error").flatMap(value => Success(value * 2)) mustBe Failure("error")
  }
}
