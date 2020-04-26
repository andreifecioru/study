package com.afecioru.apps

import com.afecioru.utils.AtomicTest._

object MapReduceApp extends App {
  def sumIt(values: Int*): Int = {
    values.reduce(_ + _)
  }

  sumIt(1, 2, 3, 4) is 10
}