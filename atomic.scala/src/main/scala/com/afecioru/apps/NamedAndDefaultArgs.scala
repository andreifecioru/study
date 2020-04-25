package com.afecioru.apps

class Family(mom: String, dad: String, kids: String*) {
  override def toString: String = s"Mom: ${mom}\nDad: ${dad}\nKids: ${kids.mkString(", ")}"
}

object NamedAndDefaultArgs extends App {
  val kids = Seq("Brother", "Sister")
  val family = new Family(dad = "Father", mom = "Mother", kids = kids:_*)

  println(family)

}