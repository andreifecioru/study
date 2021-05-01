package main.scala.com.afecioru.ch7

import java.io.File

object Listing extends App {
  val files = new File("/home/andrei").listFiles()

  for (file <- files
        if file.isFile
        if file.getName.contains("bash"))
    println(file)
}
