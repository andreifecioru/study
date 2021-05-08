package com.afecioru.study.basics

import scala.annotation.tailrec

object Recursion extends App {

  // factorial: tailrec implementation
  def factorial(n: Int): Int = {
    @tailrec
    def _fact(i: Int, acc: Int): Int = {
      if (i <= 1) acc
      else _fact(i - 1, acc * i)
    }

    _fact(n, 1)
  }

  println(factorial(5))

  // ...or
  def factorial2(n: Int): Int = (1 to n).fold(1)(_ * _)

  println(factorial2(5))

  // ...or
  def factorial3(n: Int): Int = (1 to n).product

  println(factorial2(5))

  def repeatString(aString: String, n: Int): String = {
    @tailrec
    def repeat(i: Int, acc: String): String = i match {
      case 0 => acc
      case _ => repeat(i - 1, if (acc.isEmpty) aString else s"$acc, $aString")
    }

    repeat(n, "")
  }

  println(repeatString("Andrei", 5))

  // tail-recursive fibonacci implementation
  def fibonacci(n: Int): Int = {
    @tailrec
    def fib(i: Int, acc1: Int, acc2: Int): Int =
      if (i >= n) acc2
      else fib(i + 1, acc2, acc1 + acc2)

    if (n <= 2) 1
    else fib(2, 1, 1)
  }

  // 1 1 2 3 5 8 13 21
  println(fibonacci(8))

}
