package com.afecioru.apps


object PatternMatchApp extends App {
  def matchMap(m: Map[_, _]): Unit = m match {
    case _:Map[Int, Int] => println("It's a Map of Int's")
    case _:Map[_, _] => println("It's just a Map")
    case _ => println("It's something else")
  }

  def matchArray(a: Array[_]): Unit = a match {
    case _:Array[Int] => println("It's an array of Int's")
    case _:Array[_] => println("It's just an array of something else.")
    case _ => println("Not an array")
  }

  matchMap(Map(1 -> 1))

  matchArray(Array(1, 2)) // prints: It's an array of Int's
  matchArray(Array("1", "2")) // prints: It's an array of something else.
}
