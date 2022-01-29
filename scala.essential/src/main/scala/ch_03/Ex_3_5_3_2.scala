package org.afecioru.study
package ch_03

object Dad {
  def rate(film: Film): Double = film match {
    case Film(_, _, _, Director("Clint", "Eastwood", _)) => 10.0
    case Film(_, _, _, Director("John", "McTiernan", _)) => 7.0
    case _ => 3.0
  }
}

class Ex_3_5_3_2 extends BaseTestSuite {
  import Fixtures._
  test ("Dad APIs") {
    Dad.rate(memento) mustBe 3.0
    Dad.rate(unforgiven) mustBe 10.0
    Dad.rate(predator) mustBe 7.0

  }
}
