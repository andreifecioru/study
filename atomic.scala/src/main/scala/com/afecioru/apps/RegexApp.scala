package com.afecioru.apps

import scala.util.matching.Regex;

object RegexApp extends App {
  // define a regex (a decimal number)
  val Decimal = """(-)?(\d+)(\.\d*)?""".r

  // alternative ways of defining regex
  val Decimal1 = new Regex("""(-)?(\d+)(\.\d*)?""")
  val Decimal2 = new Regex("(-)?(\\d+)(\\.\\d*)?") // need to escape `\` chars

  // some test input
  val input = "There are -11.3 items in 4.3 collections."

  // iterate though matches
  for (s <- Decimal findAllIn input) {
    println(s"Found match: $s")
  }

  // access match groups for each match (using pattern matching)
  for (Decimal(sign, int_part, dec_part) <- Decimal findAllIn input) {
    println(s"Sign: $sign") // when not present, it is set to `null`
    println(s"Integer part: $int_part")
    println(s"Decimal part: $dec_part")
  }
}
