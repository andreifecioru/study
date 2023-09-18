package com.afecioru.study.cats.chapter1

object App006_TypeClassesAndVariance extends App {

  import cats.Eq
  import cats.instances.int._ // can construct Eq[Int]
  import cats.instances.option._ // can construct Eq[Option[Int]]
  import cats.syntax.eq._

  val aComparison = Option(2) === Option(3) // true

  // this does not compile because Some[Int] is a subtype of Option[Int]
  // and Eq[Some[Int]] is not a subtype of Eq[Option[Int]]
  // val invalidComparison = Some(2) === None

  // we need variance
  class Animal
  class Cat extends Animal

  // covariant type: subtyping is propagated to the generic type
  class Cage[+T]
  val cage: Cage[Animal] = new Cage[Cat] // Cat <: Animal ==> Cage[Cat] <: Cage[Animal]

  // contravariant type: subtyping is propagated BACKWARDS to the generic type
  // usually these are used to model "actions"
  class Vet[-T]
  val vet: Vet[Cat] = new Vet[Animal] // Cat <: Animal ==> Vet[Animal] <: Vet[Cat]
  // intuition: you want a vet for cats, but I can give you something better:
  // a vet which can heal any animal (including cats)

  // Rule of thumb:
  //  - if a generic type HAS a T (like a container) ==> use covariance
  //  - if a generic type ACTS on T ==> use contravariance


  // Variance affects how TC instances are fetched

  // Contravariant TC
  trait SoundMaker[-T]
  implicit object AnimalSoundMaker extends SoundMaker[Animal]
  def makeSound[T](implicit soundMaker: SoundMaker[T]): Unit =
    println("Sound is made")

  makeSound[Animal] // ok - TC is defined at line 38
  makeSound[Cat] // ok - AnimalSoundMaker is better than a would-be CatSoundMaker (due to contravariance)

  // RULE 1: contravariant TCs can use the superclass instances if nothing is available strictly for that type

  // what won't work for Eq (it is invariant) works for SoundMaker
  implicit object OptionSoundMaker extends SoundMaker[Option[Int]]
  makeSound[Option[Int]]
  makeSound[Some[Int]]


  // Covariant TC
  trait AnimalCage[+T] {
    def lock: String
  }

  implicit object GeneralAnimalCage extends AnimalCage[Animal] {
    override def lock: String = "locking an animal"
  }

  implicit object CatCage extends AnimalCage[Cat] {
    override def lock: String = "locking a cat"
  }

  def lockInCage[T](implicit cage: AnimalCage[T]): Unit = {
    println(cage.lock)
  }

  lockInCage[Cat] // ok - compiler will inject CatCage
  // this will not compile: both AnimalCage and CatCage are compatible
  //  lockInCage[Animal]

  // RULE 2: covariant TC will always prefer the most specific TC instance
  // (but the compiler gets confused if the more generic TC instance is also present in the scope)

  // RULE 3: there is a tradeoff to be made: either favor the superclass (contravariance)
  // or the subclass (covariance) in TC instance resolution - but you can't have both

  // NOTE: The cats lib has decided to not make this tradeoff at all and all TCs are INVARIANT
  // With cats, we use the generic type in combination with smart constructors
  Option(2) === Option.empty[Int] // false

  // Or with extension methods
  import cats.syntax.option._
  2.some =!= none[Int] // true
}
