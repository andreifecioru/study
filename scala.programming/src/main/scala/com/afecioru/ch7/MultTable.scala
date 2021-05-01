package main.scala.com.afecioru.ch7

object MultTable extends App {

  val values = for {
    i <- 1 to 10
    j <- 1 to 10
    prod = (i * j).toString + (if (j == 10) "\n" else "")
    padding = " " * ((if (j == 10) 5 else 4) - prod.length)
  } yield s"$padding$prod"

  print(values.mkString(""))
}
