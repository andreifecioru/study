package com.afecioru.study.cats.chapter1

object App005_CatsIntro extends App {

  // Eq - typesafe comparison
  val aComparison = 2 == "two" // this compiles (always false) - triggers a compiler warning

  // PART 1: import TC
  import cats.Eq

  // PART 2: import the TC instances
  import cats.instances.int._ // all TCs applicable to Int

  // PART 3: TC usage
  val intEquality = Eq[Int] // fetch the implicit instance
  val aTypeSafeEquality = intEquality.eqv(2, 3) // false

  // this no longer compiles
  // val anUnsafeEquality = intEquality.eqv(2, "two")

  // PART 3.1: TC usage via extension methods
  import cats.syntax.eq._ // all extension methods for Eq
  val anotherTypeSafeComparison = 2 =!= 3 // true

  // this no longer compiles
  // val anotherUnsafeEquality = 2 === "two"

  // Extension methods are only visible in the presence of the right TC instance in current scope

  // PART 5: extend TC ops to composite types
  import cats.instances.list._ // bring Eq[List[Int]] in scope (we already have Eq[Int] in scope)
  val aListComparison = List(2) === List(3) // false

  // PART 6: create TC instances for custom types
  case class ToyCar(model: String, price: Double)

  implicit val samePriceToyCar: Eq[ToyCar] = Eq.instance[ToyCar] { (car1, car2) =>
    car1.price == car2.price
  }

  val ferrari = ToyCar("Ferrari", 29.99)
  val bmw = ToyCar("BMW", 29.00)

  ferrari === bmw // true

  // you can also import everything cats has to offer
  import cats._
  import cats.implicits._
}
