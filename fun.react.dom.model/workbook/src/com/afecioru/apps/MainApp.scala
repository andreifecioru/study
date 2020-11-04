package com.afecioru.apps

object MainApp extends App {
  def fizzbuzz(f: String => Unit): Unit = {
    val result = for (i <- Range(1, 100).inclusive) yield { i match {
        case x if x % 3 == 0 && x % 5 == 0 => "fizzbuzz"
        case x if x % 3 == 0 => "fizz"
        case x if x % 5 == 0 => "buzz"
        case x => x.toString 
      }
    }
    
    result.foreach(f)
  }

  fizzbuzz(println)
}