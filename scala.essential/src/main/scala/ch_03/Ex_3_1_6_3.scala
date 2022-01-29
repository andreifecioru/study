package org.afecioru.study
package ch_03


class Ex_3_1_6_3 extends BaseTestSuite {
  import Fixtures._

  test ("basic props tests") {
    eastwood.yearOfBirth mustBe 1930
    highPlainsDrifter.director.name mustBe "Clint Eastwood"
    invictus.isDirectedBy(nolan)
  }

  test("film copy method") {
    val drifterCopy = highPlainsDrifter.copy(name = "L'homme des hautes plaines")
    drifterCopy.name mustBe "L'homme des hautes plaines"
    drifterCopy.yearOfRelease mustBe 1973
    drifterCopy.imdbRating mustBe 7.7
    drifterCopy.director.name mustBe "Clint Eastwood"

    val inceptionCopy = inception.copy().copy().copy()
    inceptionCopy.name mustBe inception.name
    inceptionCopy.yearOfRelease mustBe inception.yearOfRelease
    inceptionCopy.imdbRating mustBe inception.imdbRating
    inceptionCopy.director.name mustBe inception.director.name

  }

}
