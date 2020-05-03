package com.afecioru.apps

import scala.util.control.Breaks._

object LoopBreakApp extends App {
  breakable {
    for(i <- 1 to 10) {
      println(i)
      
      if (i % 4 == 0) {
        println(s"Breaking out at $i")
        break
      }
    }
  }
}
