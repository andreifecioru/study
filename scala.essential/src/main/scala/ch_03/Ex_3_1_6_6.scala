package org.afecioru.study
package ch_03

class Adder(amount: Int) {
  def add(value: Int): Int = value + amount
}

class Counter3(val count: Int) {
  def adjust(adder: Adder): Counter3 = new Counter3(adder.add(count))
}

class Ex_3_1_6_6 extends BaseTestSuite {

  test("Counter API") {
    val adder = new Adder(10)
    new Counter3(100).adjust(adder).count mustBe 110
  }

}
