package com.afecioru.study.ch02.apps

import cats._
import cats.implicits._


object BooleanMonoidApp extends App {

  object Laws {
    def associative[T: Monoid](x: T, y: T, z: T): Boolean = {
      ((x |+| y) |+| z) == (x |+| (y |+| z))
    }

    def identity[T: Monoid](x: T): Boolean = {
      val id = Monoid.empty[T]
      ((id |+| x) == x) && ((x |+| id) == x)
    }
  }

  implicit val booleanMonoid: Monoid[Boolean] = Monoid.instance[Boolean](
    emptyValue = true,
    cmb = (b1, b2) => b1 && b2
  )

  for {
    x <- Seq(true, false)
    y <- Seq(true, false)
    z <- Seq(true, false)
  } {
    println(s"x: $x | y: $y | z: $z")
    assert(Laws.associative(x, y, z))

  }

  for {
    x <- Seq(true, false)
  } {
    println(s"x: $x")
    assert(Laws.identity(x))
  }
}
