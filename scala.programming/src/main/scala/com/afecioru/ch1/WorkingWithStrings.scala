package main.scala.com.afecioru.ch1

object WorkingWithStrings extends App {
  val s1 = "a"
  try{
    println(s1.toInt)
  } catch {
    case _: NumberFormatException => println(s"$s1 is not a number")
  }
}
