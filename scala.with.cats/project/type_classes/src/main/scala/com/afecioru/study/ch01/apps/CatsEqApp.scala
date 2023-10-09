package com.afecioru.study.ch01.apps

import cats._
import cats.implicits._
import com.afecioru.study.ch01.json.models.Person


object CatsEqApp extends App {

  println(s"1 == 1: ${1 === 1}")

  private val andrei = Person("Andrei", "Fecioru", 42, None)
  private val radu = Person("Radu", "Popescu", 12, "radu.popescu@email.com".some)
  private val anotherAndrei = Person("Andrei", "Fecioru", 42, None)

  implicit val personEq: Eq[Person] = Eq.instance[Person] { (p1: Person, p2: Person) => {
      p1.firstName === p2.lastName &&
      p1.lastName === p2.lastName &&
      p1.age === p2.age &&
      p1.email == p2.email
    }
  }

  println(s"andrei == anotherAndrei: ${andrei === anotherAndrei}")
  println(s"andrei == radu: ${andrei === radu}")
}
