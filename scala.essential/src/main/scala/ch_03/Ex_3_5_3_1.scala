package org.afecioru.study
package ch_03

case class Cat1(name: String, colour: String, food: String)

object ChipShop1 {
  def willServe(cat: Cat1): Boolean = {
    cat match {
      case Cat1(_, _, "chips") => true
      case _ => false
    }
  }
}

class Ex_3_5_3_1 extends BaseTestSuite {

  test ("ChipShop API") {
    val cat = Cat1("Tom", "red", "chips")
    ChipShop1.willServe(cat) mustBe true

    val anotherCat = Cat1("Lazy", "blue", "fish")
    ChipShop1.willServe(anotherCat) mustBe false
  }

}
