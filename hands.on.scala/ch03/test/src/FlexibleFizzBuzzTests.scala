package com.afecioru.ch03.exercises

import utest._


object MainAppTests extends TestSuite {
  def tests = Tests {
    test("up to 100") {
      var result = Seq[(Int, String)]()

      def action(value: Int, msg: String): Unit = {
        result = result :+ (value, msg)
      }

      FlexibleFizzBuzz(100)(action)

      assert(result.filter(_._2.nonEmpty).length == 47)
    }
  }
}
