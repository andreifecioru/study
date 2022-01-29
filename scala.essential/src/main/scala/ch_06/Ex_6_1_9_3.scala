package org.afecioru.study
package ch_06
package ex_6_1_9_3

import org.scalatest.tags.Disk

case class Film(
  name: String,
  yearOfRelease: Int,
  imdbRating: Double
)

case class Director(
  firstName: String,
  lastName: String,
  yearOfBirth: Int,
  films: Seq[Film]
)

object Fixture {
  val memento = new Film("Memento", 2000, 8.5)
  val darkKnight = new Film("Dark Knight", 2008, 9.0)
  val inception = new Film("Inception", 2010, 8.8)
  val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
  val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9)
  val unforgiven = new Film("Unforgiven", 1992, 8.3)
  val granTorino = new Film("Gran Torino", 2008, 8.2)
  val invictus = new Film("Invictus", 2009, 7.4)
  val predator = new Film("Predator", 1987, 7.9)
  val dieHard = new Film("Die Hard", 1988, 8.3)
  val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
  val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)
  val eastwood = new Director("Clint", "Eastwood", 1930,
    Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))
  val mcTiernan = new Director("John", "McTiernan", 1951,
    Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))
  val nolan = new Director("Christopher", "Nolan", 1970,
    Seq(memento, darkKnight, inception))
  val someGuy = new Director("Just", "Some Guy", 1990,
    Seq())
  val directors = Seq(eastwood, mcTiernan, nolan, someGuy)
}

object Exercises{
  import Fixture._

  def directorsWithMoreFilmsThan(numberOfFilms: Int): Seq[Director] =
    directors.filter(_.films.size > numberOfFilms)

  def directorBornBefore(year: Int): Option[Director] =
    directors.find(_.yearOfBirth < year)

  def directorsBornBeforeWithMoreFilmsThan(year: Int, numberOrFilms: Int): Seq[Director] =
    directors.view
      .filter(_.films.size > numberOrFilms)
      .filter(_.yearOfBirth < year)
      .toSeq

  def directorsByAge(ascending: Boolean): Seq[Director] =
    if (ascending)
      directors.sortWith(_.yearOfBirth < _.yearOfBirth)
    else
      directors.sortWith(_.yearOfBirth > _.yearOfBirth)
}


class Ex_6_1_9_3 extends BaseTestSuite {
  import Exercises._
  import Fixture._

  test("exercise one") {
    directorsWithMoreFilmsThan(3) mustBe Seq(eastwood, mcTiernan)
  }

  test("exercise two") {
    directorBornBefore(1980) mustBe Option(eastwood)
  }

  test("exercise three") {
    directorsBornBeforeWithMoreFilmsThan(1950, 3) mustBe Seq(eastwood)
  }

  test("exercise four") {
    directorsByAge(true) mustBe Seq(eastwood, mcTiernan, nolan, someGuy)
    directorsByAge(false) mustBe Seq(someGuy, nolan, mcTiernan, eastwood)
  }
}
