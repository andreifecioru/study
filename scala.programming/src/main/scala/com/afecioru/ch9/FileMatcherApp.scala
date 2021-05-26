package main.scala.com.afecioru.ch9

import java.io.File

trait FileMatcher {
  def _pattern: String
  def apply(fileName: String): Boolean
}

object FileMatcher {
  def matchFiles(path: String, matcher: FileMatcher): Seq[File] = {
    for (file <- new File(path).listFiles.toIndexedSeq if matcher(file.getName))
      yield file
  }
}

object FileMatcherApp  extends App {
  def endsWithMatcher(pattern: String): FileMatcher = {
    new FileMatcher {
      override final def _pattern: String = pattern
      override final def apply(fileName: String): Boolean = fileName.endsWith(pattern)
    }
  }

  def containsMatcher(pattern: String): FileMatcher = {
    new FileMatcher {
      override final def _pattern: String = pattern
      override final def apply(fileName: String): Boolean = fileName.contains(pattern)
    }
  }

  FileMatcher.matchFiles("/home/andrei", endsWithMatcher("rc"))
    .foreach(println)

  println("\n=================================\n")

  FileMatcher.matchFiles("/home/andrei", containsMatcher("bash"))
    .foreach(println)
}
