package com.afecioru.study.ch01.printable.typeclasses

import com.afecioru.study.ch01.printable.models.Cat

trait Printable[T] {
  def format(input: T): String
}

object Printable {
  implicit val intPrintable: Printable[Int] =
    (input: Int) => input.toString

  implicit val stringPrintable: Printable[String] =
    (input: String) => input

  implicit val catPrintable: Printable[Cat] =
    (cat: Cat) => s"${cat.name} is a ${cat.age} year old ${cat.color} cat"

  def format[T](input:T)
               (implicit printable: Printable[T]): String =
    printable.format(input)
}

object PrintableSyntax {
  implicit class PrintableExtension[T](val input: T) {
    def format(implicit printable: Printable[T]): String =
      printable.format(input)
  }
}