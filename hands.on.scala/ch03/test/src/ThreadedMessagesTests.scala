package com.afecioru.ch03.exercises

import utest._


object ThreadedMessagesTests extends TestSuite {
  def tests = Tests {
    test("7 nested messages ") {
      var messages = Seq(
        Message(0, None, "Hello"),
        Message(1, Some(0), "World"),
        Message(2, None, "I am Cow"),
        Message(3, Some(2), "Hear me moo"),
        Message(4, Some(2), "Here I stand"),
        Message(5, Some(2), "I am Cow"),
        Message(6, Some(5), "Hear me moo, moo"),
      )

      ThreadedMessages.print(messages)
    }
  }
}
