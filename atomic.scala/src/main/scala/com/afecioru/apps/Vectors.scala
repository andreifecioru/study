package com.afecioru.apps

import com.afecioru.utils.AtomicTest._

object Vectors extends App {
  val sentence = "The dog visited the firehouse"

  val words: Vector[String] = sentence.split(" ").toVector

  val reversedWords = for (word <- words) yield {
    word.reverse
  }

  for (word <- reversedWords) {
    println(word)
  }

  println("--------")

  reversedWords.reverse.map(_.reverse).foreach(println)

}