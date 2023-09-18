package com.afecioru.study.cats.chapter2

object App002_Monoids extends App {

  import cats.Semigroup
  import cats.instances.int._
  import cats.syntax.semigroup._

  val nums = (1 to 1000).toList

  // |+| is always associative
  val sumLeft = nums.foldLeft(0)(_ |+| _)
  val sumRight = nums.foldRight(0)(_ |+| _)

  assert(sumLeft == sumRight)

  // Semigroups are not sufficient for fold operations (only reduce)
  // We need a Monoid: which is a semigroup with a empty/zero element

  import cats.Monoid
  // import cats.syntax.monoid._

  // define a general API
  def combineFold[T: Monoid](list: List[T]): T = {
    val monoid = implicitly(Monoid[T])
    // NOTE: Monoid extends Semigroup so it can use the |+| operator
    list.foldLeft(monoid.empty)(_ |+| _)
  }

  println(combineFold(nums))

  import cats.instances.option._
  val optNums = nums.map(Option(_)) :+ None
  println(combineFold(optNums))


  // Exercise: combine a list of "phonebooks"
  val phonebooks = List(
    Map(
      "Alice" -> 235,
      "Bob" -> 647
    ),
    Map(
      "Charlie" -> 372,
      "Daniel" -> 889
    ),
    Map(
      "Tina" -> 123
    )
  )

  import cats.instances.map._
  println(combineFold(phonebooks))

  // Exercise: define Monoid for a custom type
  case class ShoppingCart(items: List[String], total: Double)
  object ShoppingCart {
    val empty: ShoppingCart = ShoppingCart(List.empty, 0.0)

    implicit val cartMonoid: Monoid[ShoppingCart] = Monoid.instance[ShoppingCart](
      empty,
      (c1, c2) => {
        ShoppingCart(
          c1.items ++ c2.items,
          c1.total + c2.total
        )
      }
    )
  }

  def checkout(carts: List[ShoppingCart]): ShoppingCart =
    combineFold(carts)

  val carts = List(
    ShoppingCart(List("Books", "Shirt"), 100),
    ShoppingCart(List("Bucket", "Shovel"), 200),
    ShoppingCart(List("Dishes"), 300)
  )
  println(checkout(carts))

}
