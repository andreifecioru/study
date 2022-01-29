package org.afecioru.study
package ch_03

class Ex_3_1_6_1 extends BaseTestSuite {
  import  Cats._

  test("object props are set") {
    assert(oswald.name === "Oswald")
    assert(henderson.colour === "Ginger")
    assert(quentin.food === "Curry")
  }

}
