package main.scala.com.afecioru.ch1

import scala.annotation.tailrec

object BigIntFactorial extends App {
  def factorial(num: BigInt): BigInt = {
    @tailrec
    def _compute(_num: BigInt, acc: BigInt = 1): BigInt = {

      if (_num <= 1) acc
      else _compute(_num - 1, acc * _num)
    }
    _compute(num)
  }
  println(s"30! is ${factorial(30)}")
}
