package main.scala.com.afecioru.ch7

import java.io.File
import scala.io.Source
import scala.collection.mutable.{Set => MSet}

object Grepping extends App {
  def fileLines(file: File): (Iterator[String], Source) = {
    val source = Source.fromFile(file)
    (source.getLines(), source)
  }

  def grep(pattern: String, path: String): List[String] = {
    val sources = MSet.empty[Source]
    val lines = (for (file <- new File(path).listFiles()
            if file.isFile
            if file.getName.endsWith(".scala");
         (lines, source) = fileLines(file);
         line <- lines
            if line.matches(pattern))
      yield {
        sources += source
        line
      }).toList

    sources.foreach(_.close())
    lines
  }

  grep(".*grep.*", ".").foreach(println)

}
