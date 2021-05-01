package main.scala.com.afecioru.ch3

import scala.io.Source

object ReadLinesFromFile extends App {
  def widthOfLength(s: String): Int = s.length.toString.length

  if (args.length > 0) {
    val file = Source.fromFile(args(0))
    val lines = file.getLines().toList

    val maxWidthOfLength = lines.map(widthOfLength).max

    lines.map { line =>
      val padding = " " * (maxWidthOfLength - widthOfLength(line))
      s"$padding${line.length} : $line"
    }.foreach(println)

    file.close()
  } else {
    Console.err.println("Please enter file name")
  }
}
