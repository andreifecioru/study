package com.afecioru.study.cats.chapter2

object App001_Semigroups extends App {

  // Semigroups
  //   - COMBINE elements of the same type
  //   - the COMBINE operation is associative

  import cats.Semigroup
  import cats.instances.int._
  import cats.instances.string._

  val naturalIntSemigroup = Semigroup[Int]
  val intCombination = naturalIntSemigroup.combine(10, 20) // addition

  println(intCombination)

  val naturalStringSemigroup = Semigroup[String]
  val stringCombination = naturalStringSemigroup.combine("I love ", "Cats") // concatenation
  println(stringCombination)

  // The semigroup's combine method can be used for reducing collections

  def reduceInt(nums: List[Int]): Int =
    nums.reduce(naturalIntSemigroup.combine)

  val nums = (1 to 10).toList
  println(reduceInt(nums))


  def reduceString(strings: List[String]): String =
    strings.reduce(naturalStringSemigroup.combine)

  val strings = List("I ", "love ", "Cats")
  println(reduceString(strings))

  // Let's define a general API to reduce collections
  def reduceThings[T](list: List[T])
                     (implicit semigroup: Semigroup[T]): T = {
    list.reduce(semigroup.combine)
  }
  println(reduceThings(nums))
  println(reduceThings(strings))

  import cats.instances.option._
  val options = nums.map(Option(_)) :+ None
  println(options)
  println(reduceThings(options))

  // Exercise 1: define a semigroup for a custom type
  case class Expense(id: Long, amount: Double)
  implicit val expenseSemigroup = Semigroup.instance[Expense] { (e1, e2) =>
    Expense(
      id = Math.max(e1.id, e2.id) + 1,
      amount = e1.amount + e2.amount
    )
  }

  val expenses = List(
    Expense(1L, 10),
    Expense(2L, 10),
    Expense(3L, 10),
    Expense(4L, 10),
  )

  println(reduceThings(expenses))

  // Extensions methods for semigroups - |+|
  import cats.syntax.semigroup._
  val anIntSum = 2 |+| 3
  val expenseSum = Expense(1L, 10) |+| Expense(2L, 20)

  // Exercise 2: generic reducer using the |+| operator
  def reduceThings2[T: Semigroup](list: List[T]): T = { // with type context (remove the implicit param)
    list.reduce(_ |+| _)
  }

  println(reduceThings2(expenses))


}
