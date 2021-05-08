package com.afecioru.study.basics

import scala.annotation.tailrec

object Functions extends App {

  def repeatString(aString: String, n: Int): String =
    (1 to n)
      .map(_.toString) // we have to go to string 1st, if we want fold() to produce strings
      .fold("")((acc, _) => if (acc.isEmpty) aString else s"$acc, $aString")

  println(repeatString("Andrei", 5))

  // NOTE: you must specify the return type for recursive functions.
  def repeatStringRecursive(aString: String, n: Int): String = n match {
    case 0 => ""
    case 1 => aString
    case _ => s"$aString, ${repeatStringRecursive(aString, n - 1)}"
  }

  println(repeatStringRecursive("Andrei", 3))

  def factorial(n: Int): Int = n match {
    case _ if n <= 1 => 1
    case _ => n * factorial(n - 1)
  }

  println(factorial(5))

  def fibonacci(n: Int): Int = n match {
    case _ if n <= 2 => 1
    case _ => fibonacci(n - 1) + fibonacci(n - 2)
  }

  println(s"fib(6) = ${fibonacci(6)}")

  def fibonacci_2(n: Int): Int = {
    @tailrec
    def _compute(_n: Int = 0, acc: (Int, Int) = (0, 1)): (Int, Int) = {
      if (_n == n) acc
      else _compute(_n + 1, (acc._2, acc._1 + acc._2))
    }

    _compute()._1
  }

  println(s"fib(6) = ${fibonacci_2(6)}")

  def isPrime(n: Int): Boolean = {
    @tailrec
    def checkPrime(x: Int): Boolean = x match {
      case _ if x > (n / 2) => true
      case _ if n % x == 0 => false
      case _ => checkPrime(x + 1)
    }

    n match {
      case _ if n <= 3 => true
      case _ => checkPrime(2)
    }
  }

  println(isPrime(8))
  println(isPrime(31))
}
