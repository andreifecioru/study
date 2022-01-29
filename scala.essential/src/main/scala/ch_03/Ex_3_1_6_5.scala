package org.afecioru.study
package ch_03

class Counter2(val count: Int) {
  def inc(amount:Int = 1): Counter2 = new Counter2(count + amount)
  def dec(amount:Int = 1): Counter2 = new Counter2(count - amount)
}

class Ex_3_1_6_5 extends BaseTestSuite {

  test("Counter API") {
    new Counter2(10).inc().dec(5).inc(3).dec().count mustBe 8
  }

}
