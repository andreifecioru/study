package main.scala.com.afecioru.ch11

class Person(val firstName: String, val lastName: String) {
  override def toString: String = firstName
}

class PersonVal(val person: Person) extends AnyVal {
  override def toString: String = s"${person.firstName} ${person.lastName}"
}

object ValClassApp extends App {
  val p1 = new Person("Andrei", "Fecioru")
  println(p1)

  val p2 = new PersonVal(p1)
  println(p2)
}
