package com.afecioru.apps

import com.afecioru.utils.AtomicTest._

object TestApp extends App {
  val name = "Andrei"

  name is "Andrei"

  name is "Radu"
}
