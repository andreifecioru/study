package com.afecioru.study.ch01.apps

import cats._
import cats.implicits._
import com.afecioru.study.ch01.printable.models.Cat

object CatsShowApp extends App {

  println("123".show)
  println("Andrei".show)
  println(Map("a" -> 1, "b" -> 2, "c" -> 3).show)

  implicit val catShow: Show[Cat] = Show.show[Cat] { cat =>
    s"${cat.name} is a ${cat.age} year old ${cat.color} cat"
  }

  println(Cat("Tom", 10, "grey").show)

}
