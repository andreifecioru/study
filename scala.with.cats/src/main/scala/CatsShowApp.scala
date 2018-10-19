import cats._
import cats.implicits._

import model.Person


object CatsShowApp extends App {
//  implicit val personShow: Show[Person] = Show.fromToString
  implicit val personShow: Show[Person] = Show.show(p => s"${p.first} ${p.last}")

  val person = Person("John", "Doe")
  println(person.show)
  println(List(Person("John", "Doe")).show)
  println(person.some)
}