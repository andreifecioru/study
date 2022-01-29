package org.afecioru.study
package ch_05
package ex_5_4_1_1

case class Pair[T, U](one: T, two: U)

class Ex_5_4_1_1 extends BaseTestSuite {
  val testData: Pair[String, Int] = Pair("hi", 2)

  test("generic product type") {
    testData.one mustBe "hi"
    testData.two mustBe 2
  }
}
