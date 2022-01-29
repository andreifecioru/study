package org.afecioru.study
package ch_03

class Person(val firstName: String, val lastName: String)

object Person {
  def apply(fullName: String): Person = {
    fullName.split(" ").toList match {
      case firstName :: lastName :: Nil => new Person(firstName, lastName)
      case _ => throw new IllegalArgumentException("Invalid name format")
    }
  }
}


class Ex_3_3_2_1 extends BaseTestSuite {

  test ("Person#apply() extracts first and last name") {
    val person = Person("John Doe")
    person.firstName mustBe "John"
    person.lastName mustBe "Doe"
  }

  test ("Person#apply() throws exception when invalid name is passed") {
    val error = intercept[IllegalArgumentException] {
      Person("John-Doe")
    }

    error.getMessage mustBe "Invalid name format"
  }

}
