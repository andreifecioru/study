package com.afecioru.study.basics

object StringOps extends App {

  // conversions
  println("aaa".toIntOption)

  // prepending and appending
  val name = "Andrei"
  println('*' +: name :+ '!')

  // Interpolators

  // S-interpolators
  println(s"Name is: $name")

  // F-interpolators: use printf-like formatted strings
  val taxRate = .234f
  println(f"Tax tate is: $taxRate%2.2f")

  // raw-interpolator: use special chars w/o escaping
  println(raw"No new lines \n here!")

  val str = "No new lines \n here"
  println(raw"$str") // in this case the `\n` will *not* be escaped.

}
