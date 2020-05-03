package com.afecioru.apps


object LayoutApp extends App {
  import com.afecioru.components.LayoutElement._

  val ae = element(Array("12345", " abc "))
  println(s"Width: ${ae.height}")
  println(s"Height: ${ae.width}")

  val ue1 = element('x', 5, 3)
  val ue2 = element('o', 5, 3)
  println("-------")
  println(ue1 above ue2)
  println("-------")
  println(ue1 below ue2)
  println("-------")
  println(ue1 before ue2)
  println("-------")
  println(ue1 after ue2)

  println("\n\n\n")
  val elem1 = element('x', width = 3, height = 7)
  val elem2 = element('o', width=5, height= 3)

  println(elem1 above elem2)
  println("-------")
  println(elem1 before elem2)
}
