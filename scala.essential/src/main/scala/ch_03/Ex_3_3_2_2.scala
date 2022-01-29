package org.afecioru.study
package ch_03

class Ex_3_3_2_2 extends BaseTestSuite {
  import Fixtures._

  test("determine older director") {
    Director.older(eastwood, nolan) mustBe eastwood
  }

  test("determine highest film rating") {
    Film.highestRating(inception, memento) mustBe 8.8
  }

  test("determine oldest director at time of filming") {
    Film.oldestDirectorAtTheTime(inception, invictus) mustBe eastwood
  }

}
