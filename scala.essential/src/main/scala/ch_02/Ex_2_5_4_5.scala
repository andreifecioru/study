package org.afecioru.study
package ch_02

object person {
  val firstName = "John"
  val lastName = "Smith"
}

object alien {
  // the type of the parameter here is the "singleton type" of the `person` object
  def greet(p: person.type): String = s"Hello, ${p.firstName} ${p.lastName}!"
}


class Ex_2_5_4_5 extends BaseTestSuite {

  test ("alien greeting") {
    val greeting = alien.greet(person)

    greeting mustBe "Hello, John Smith!"
  }

}
