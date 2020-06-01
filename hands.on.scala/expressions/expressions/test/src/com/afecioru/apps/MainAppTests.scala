package com.afecioru.apps

import utest._


object MainAppTests extends TestSuite {
  def tests = Tests {
    test("hello") {
      val result = MainApp.hello()
      assert(result == "Hello World")
      result
    }
  }
}
