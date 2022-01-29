package org.afecioru.study
package ch_03

case class Counter4(count: Int = 0) {
  def inc(amount: Int): Counter4 = this.copy(count = count + amount)
}

class Ex_3_4_5_3 extends BaseTestSuite {

  test("Counter API") {
    Counter4().inc(10).count mustBe 10
  }
}
