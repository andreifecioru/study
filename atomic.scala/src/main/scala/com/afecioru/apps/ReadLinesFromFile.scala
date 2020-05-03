package com.afecioru.apps

import scala.language.postfixOps

import sys.process._
import scala.io.Source


object ReadLinesFromFileApp extends App {
  val workDir: String = ("pwd" !!).stripLineEnd
  var fileName: String = s"$workDir/build.sbt"
  println(s"Reading file: $fileName")

  Source.fromFile(fileName)
    .getLines()
    .zipWithIndex
    .map { 
      case (line, idx) => s"[$idx]\t$line" 
    }
    .foreach(println)
}