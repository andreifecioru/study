package com.afecioru.study.cats.chapter1

import cats.Eval

object App001_Welcome extends App{

  private val later = Eval.later {
    println("Andrei")
    42
  }

  println(later.value)

}
