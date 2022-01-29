package org.afecioru.study
package ch_02

import org.scalatest.funsuite.AnyFunSuite

object Oswald {
  val name: String = "Oswald"
  val colour: String = "Black"
  val food: String = "Milk"
}

object Henderson {
  val name: String = "Henderson"
  val colour: String = "Ginger"
  val food: String = "Chips"
}

object Quentin {
  val name: String = "Quentin"
  val colour: String = "Tabby and white"
  val food: String = "Curry"
}


class Ex_2_4_5_1 extends AnyFunSuite {

  test("object props are set") {
    assert(Oswald.name === "Oswald")
    assert(Henderson.colour === "Ginger")
    assert(Quentin.food === "Curry")
  }

}
