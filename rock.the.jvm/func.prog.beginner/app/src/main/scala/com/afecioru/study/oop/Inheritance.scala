package com.afecioru.study.oop


object Inheritance extends App {
  // Scala supports single-class inheritance but does allow the mixing of multiple traits


  class Animal {
    // members (i.e methods and fields) are *public* by default
    val creatureType: String = "wild"
    def walk(): Unit = println("Animal is walking")

    // prevent overriding
    final def breathe(): Unit = println("Animal is breathing")

    protected def eat(): Unit  = println("Animal is eating...")
  }

  // sealing a class allows for extension only in the *same* file
  sealed class Cat(
    // fields can be overridden directly in the constructor definition
    override val creatureType: String = "domestic"
  ) extends Animal {
    def feed(): Unit = eat() // access to the protected method from the base class

    override def walk(): Unit = {
      super.walk()  // access the method with the same name in the base class
      println("But this one is walking quietly.")
    }
  }

  // prevent further inheritance
  final class Dog extends Animal {
    // field overriding
    override val creatureType: String = "domestic"
    // method overriding
    override def walk(): Unit = println("Dog is walking...")
  }

  // inheritance and constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  // need to pass the constructor args to the base class (to one of the available constructors)
  class Child(name: String) extends Person(name)
  class Adult(name: String, age: Int, idCars: String) extends Person(name, age)

  val aCat = new Cat("feline")
  aCat.feed()
  aCat.walk()
  println(aCat.creatureType)

  val aDog = new Dog()
  aDog.walk()

  // type substitution (broad: polymorphism)
  val anAnimal: Animal = new Dog()
  anAnimal.walk()
}
