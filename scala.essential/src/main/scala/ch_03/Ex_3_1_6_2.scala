package org.afecioru.study
package ch_03

object ChipShop {
  def willServe(cat: Cat): Boolean = cat.food == "Chips"
}

class Ex_3_1_6_2 extends BaseTestSuite {
  import Cats._

  test ("likes chips") {
    ChipShop.willServe(oswald) mustBe false
    ChipShop.willServe(henderson) mustBe true
  }

}
