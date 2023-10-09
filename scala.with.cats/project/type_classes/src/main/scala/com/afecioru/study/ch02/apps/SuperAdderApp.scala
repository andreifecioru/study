package com.afecioru.study.ch02.apps

import cats._
import cats.implicits._


object SuperAdderApp extends App {
  final case class Order(totalCost: Double, quantity: Double)
  object Order {
    implicit object OrderSemigroup extends Semigroup[Order] {
      override def combine(o1: Order, o2: Order): Order =
        Order(
          o1.totalCost + o2.totalCost,
          o1.quantity + o2.quantity
        )
    }
  }

  object SuperAdder {
    def add[T : Semigroup](items: List[T]): T =
      items.reduce(_ |+| _)
  }

  val nums = (1 to 10).toList
  val opts = nums.map(Option(_))
  val orders = List(
    Order(1.0, 10.0),
    Order(2.0, 20.0),
    Order(3.0, 30.0),
    Order(4.0, 40.0)
  )

  println(s"Adding nums: ${SuperAdder.add(nums)}")
  println(s"Adding opts: ${SuperAdder.add(opts)}")
  println(s"Adding orders: ${SuperAdder.add(orders)}")

}
