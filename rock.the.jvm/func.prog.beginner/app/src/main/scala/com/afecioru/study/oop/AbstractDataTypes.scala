package com.afecioru.study.oop

object AbstractDataTypes extends App {

  // abstract classes have members (i.e. fields and methods) w/o values/implementations
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal {
    // NOTE: `override` qualifier is not mandatory for abstract members (but it is recommended)
    override val creatureType: String = "canine"
    override def eat: Unit = println("Dog eating...")
  }

  // traits - ultimate abstract constructs
  trait Carnivore {
    def hunt(animal: Animal): Unit
  }

  trait ColdBlooded

  // multiple traits can be mixed into a class
  class Crocodile extends Animal
    with Carnivore
    with ColdBlooded {
    override val creatureType: String = "reptile"

    override def eat: Unit = println("Crocodile is eating...")

    override def hunt(animal: Animal): Unit = println(s"Crocodile is huting ${animal.creatureType}")
  }

  val aDog = new Dog()
  val aCroc = new Crocodile()

  aCroc.hunt(aDog)

  /** Traits vs. abstract classes (comparison):
   *    - traits cannot have constructor parameters
   *    - you can extend at most one class, but you can mixin multiple traits
   *    - traits describe a behavior while abstract classes define entities (is-a relationship)

  * */

}
