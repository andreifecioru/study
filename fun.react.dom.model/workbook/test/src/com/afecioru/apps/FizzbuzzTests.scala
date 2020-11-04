package com.afecioru.apps

import utest._

object FizzbuzzTests extends TestSuite {
  import MainApp._
  def tests = Tests {
    test("hello") {
      val result = new Array[String](100)
      var idx = 0

      fizzbuzz { s =>
        result(idx) = s
        idx += 1
      }

      Array("1", "2", "fizz", "4", "buzz").zipWithIndex.foreach {
        case (elem, idx) => assert(result(idx) == elem)
      }
    }
  }
}
