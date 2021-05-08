package com.afecioru.study.basics

object CallByNameOrByValue extends App {
  def callByValue(x: Int): Unit = {
    println("Call by value.")
    println(x)
    println(x)
  }

  callByValue {
    println("Computing value.")
    42
  }

  println("\n\n--------------------------\n\n")


  def callByName(x: => Int): Unit = {
    println("Call by name.")
    println(x)
    println(x)
  }

  callByName {
    println("Computing value")
    42
  }

}
