package com.afecioru.ch03.exercises

object FlexibleFizzBuzz {
  def apply(upTo: Int)(action: (Int, String) => Unit): Unit = {
    for (value <- 1 to upTo) {
      if ((value % 3 == 0) && (value % 5 == 0)) action(value, "FizzBuzz")
      else if (value % 3 == 0) action(value, "Fizz")
      else if (value % 5 == 0) action(value, "Buzz")
      else action(value, "")
    }
  }
}
