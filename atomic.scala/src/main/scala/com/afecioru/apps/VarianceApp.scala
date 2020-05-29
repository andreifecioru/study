package com.afecioru.apps

class DataContainer[+T](init: T) {
  // w/o the `private[this]` modifier we would break variance checker rules
  private[this] var _value: T = init

  def value: T = _value
  // we cannot use T (covariant type param) as input
  // param in the setter (a contra-variant position)
  // we use a new type U whose *lower bound* is T
  def update[U >: T](newValue: U): DataContainer[U] = new DataContainer[U](newValue)

  override def toString: String = s"Value: $value"
}

class Person() {
  override def toString: String = "Person"
}

class Employee() extends Person {
  override def toString: String = "Employee"
}

object VarianceApp extends App {
  // put an employee (this becomes a data container of employees)
  val employeeContainer = new DataContainer(new Employee)
  println(employeeContainer)

  // put a person (we can only put super-types of Employee)
  val personContainer = employeeContainer.update(new Person)
  println(personContainer)
}
