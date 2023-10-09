package com.afecioru.study.ch01.apps

import com.afecioru.study.ch01.printable.models.Cat
import com.afecioru.study.ch01.printable.typeclasses.{Printable, PrintableSyntax}

object PrintableApp extends App {
  import com.afecioru.study.ch01.printable.typeclasses.PrintableSyntax._

  val cat = Cat("Tom", 10, "grey")

  println(Printable.format(cat))
  println(cat.format)

}
