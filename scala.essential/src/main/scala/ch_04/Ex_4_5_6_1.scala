package org.afecioru.study
package ch_04
package ex_4_5_6_1

sealed trait TrafficLight {
  def next: TrafficLight
}

case object Red extends TrafficLight {
  override val next: TrafficLight = Green
}

case object Green extends TrafficLight {
  override val next: TrafficLight = Yellow
}

case object Yellow extends TrafficLight {
  override val next: TrafficLight = Red
}

// another way is to implement next inside the TrafficLight trait
object CycleLights {
  def next(light: TrafficLight): TrafficLight = light match {
    case Red => Green
    case Green => Yellow
    case Yellow => Red
  }
}


class Ex_4_5_6_1 extends BaseTestSuite {

  test ("polymorphic traffic light cycle") {
    Red.next mustBe Green
    Green.next mustBe Yellow
    Yellow.next mustBe Red
  }

  test ("pattern-match traffic light cycle") {
    CycleLights.next(Red) mustBe Green
    CycleLights.next(Green) mustBe Yellow
    CycleLights.next(Yellow) mustBe Red
  }

}
