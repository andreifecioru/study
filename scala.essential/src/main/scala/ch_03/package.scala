package org.afecioru.study

package object ch_03 {

  class Cat(val name: String, val colour: String, val food: String)

  object Cats {
    val oswald = new Cat("Oswald", "Black", "Milk")
    val henderson = new Cat("Henderson", "Ginger", "Chips")
    val quentin = new Cat("Quentin", "Tabby and white", "Curry")
  }

  // -----------------------------------------------------

  class Director(val firstName: String, val lastName: String, val yearOfBirth: Int) {
    def name: String = s"$firstName $lastName"
  }

  object Director {
    def apply(firstName: String, lastName: String, yearOfBirth: Int): Director =
      new Director(firstName, lastName, yearOfBirth)

    def unapply(director: Director): Option[(String, String, Int)] =
      Some(director.firstName, director.lastName, director.yearOfBirth)

    def older(director_1: Director, director_2: Director): Director =
      if (director_1.yearOfBirth <= director_2.yearOfBirth) director_1 else director_2
  }

  class Film(val name: String, val yearOfRelease: Int,
             val imdbRating: Double, val director: Director) {
    def directorsAge: Int = yearOfRelease - director.yearOfBirth
    def isDirectedBy(director: Director): Boolean = this.director.name == director.name

    def copy(name: String = this.name, yearOfRelease: Int = this.yearOfRelease,
             imdbRating: Double = this.imdbRating, director: Director = this.director): Film =
      new Film(name, yearOfRelease, imdbRating, director)
  }

  object Film {
    def apply(name: String, yearOfRelease: Int, imdbRating: Double, director: Director): Film =
      new Film(name, yearOfRelease, imdbRating, director)

    def unapply(film: Film): Option[(String, Int, Double, Director)] =
      Some(film.name, film.yearOfRelease, film.imdbRating, film.director)

    def highestRating(film_1: Film, film_2: Film): Double =
      Seq(film_1, film_2).map(_.imdbRating).max

    def oldestDirectorAtTheTime(film_1: Film, film_2: Film): Director =
      Director.older(film_1.director, film_2.director)
  }

  object Fixtures {
    val eastwood = new Director("Clint", "Eastwood", 1930)
    val mcTiernan = new Director("John", "McTiernan", 1951)
    val nolan = new Director("Christopher", "Nolan", 1970)
    val someBody = new Director("Just", "Some Body", 1990)

    val memento = new Film("Memento", 2000, 8.5, nolan)
    val darkKnight = new Film("Dark Knight", 2008, 9.0, nolan)
    val inception = new Film("Inception", 2010, 8.8, nolan)
    val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7, eastwood)
    val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9, eastwood)
    val unforgiven = new Film("Unforgiven", 1992, 8.3, eastwood)
    val granTorino = new Film("Gran Torino", 2008, 8.2, eastwood)
    val invictus = new Film("Invictus", 2009, 7.4, eastwood)
    val predator = new Film("Predator", 1987, 7.9, mcTiernan)
  }

}
