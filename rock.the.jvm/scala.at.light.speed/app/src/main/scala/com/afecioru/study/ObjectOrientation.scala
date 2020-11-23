package com.afecioru.study

object ObjectOrientation extends App {

  // minimal class definition
  class Animal {
    // fields and method are 'private' by default

    // fields
    val age: Int = 0

    // methods
    def eat(): Unit = println("I am eating")
  }

  // Use `new` to instantiate classes
  val dog = new Animal

  // basic inheritance
  class Dog(
    val name: String  // constructor args (are not fields by default, use `val` for that)
  ) extends Animal // this is the constructor definition

  val aDog = new Dog("Spike")
  println(s"The dog name is: ${aDog.name}")
  aDog.eat()

  // sub-type polymorphism
  val anAnimal = new Dog("Rex")
  anAnimal.eat() // the most derived method will be called at run-time

  // abstract classes
  abstract class WalkingAnimal {
    val hasLegs = true

    def walk(): Unit // no-implementation (abstract method)
  }

  // "interface" = ultimate abstract type (all fields are abstract)
  trait Carnivore {
    def hunt(animal: Animal): Unit
  }

  trait AnotherTrait

  // we can extend a single class but mix-in multiple traits
  class Crocodile extends Animal with Carnivore with AnotherTrait {
    // we have to implement all abstract fields (or declare the class abstract)
    override def hunt(animal: Animal): Unit = println("I am hunting an animal")

    // we can call upon inherited methods via `super`
    override def eat(): Unit = {
      hunt(new Dog("Mimi"))
      super.eat()
    }
  }
  println("The crocodile wants to eat")
  val aCroc = new Crocodile
  aCroc.eat()

  // infix notation for method invocation (with one arg)
  // NOTE: operators in Scala are actually methods.
  //       1 + 2  is actually equivalent to  1.+(2)
  aCroc hunt aDog

  // method names are very permissive
  trait Philosopher {
    def ?!(thought: String): Unit = println(s"I am thinking: $thought")
  }

  (new Philosopher {} /* anonymous trait instantiation */) ?! "Boo!"

  // objects: define a type and a singleton of that type
  object MySingleton { // this is the only singleton of the MySingleton type
    val someVal = 42

    // makes `MySingleton` callable (same as __call__ in Python)
    def apply(x: Int): Int = x + 1
  }
  println(MySingleton.someVal)
  println(MySingleton(1)) // calls the `apply()` method

  object Animal { // companion object for the Animal class
    // NOTE:
    //    - must be defined in the same file as the class
    //    - can access private fields in companion class
    //    - companion class can access private fields in the companion object

    val someField = 42 // similar with static fields in Java
  }

  // accessing fields in the companion object
  Animal.someField

  // case classes === data classes with some auto-generated code
  //  - sensible implementations for equals and hash-code
  //  - serialization
  //  - companion object definition (with apply/unapply implementation)
  //  - toString() implementation

  case class Person(name: String)
  val aPerson = Person("Andrei")  // equiv. to Person.apply("Andrei")
  println(s"Name: ${aPerson.name}")

  // exceptions: usually used when interfacing with Java code
  // NOTE: try/catch are expressions
  val errorMsg =  try {
    val x: String = null
    x.length // triggers NPE
  } catch {
    case _: NullPointerException => "null pointer exception"
  } finally {
    println("This is executed no matter what")
  }
  println(s"Error messages: $errorMsg")


  // generics (definition-side)
  abstract class MyList[T] {
    val head: T
    val tail: MyList[T]
  }

  // using generics at call-site
  val aList: List[Int] = List(1, 2, 3)

  val reversedList = aList.reverse // returns an new list (we are working with immutable lists by default)
}
