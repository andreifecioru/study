package org.afecioru.study
package ch_03

class Counter(val count: Int) {
  def inc: Counter = new Counter(count + 1)
  def dec: Counter = new Counter(count - 1)
}


class Ex_3_1_6_4 extends BaseTestSuite {

  test ("Counter API") {
    new Counter(10).inc.dec.inc.inc.count mustBe 12
  }

}
