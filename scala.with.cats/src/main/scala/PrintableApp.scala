import model.Person

import fun.Printable
import fun.PrintableInstances._
import fun.PrintableSyntax._

import cats.Eq

object PrintableApp extends App {
  Printable.print(5)
  Printable.print(false)
  Printable.print(Option(5))
  Printable.print(Option.empty[Int])
  Printable.print(Seq(1, 2, 3))

  implicit val personPrintable: Printable[Person] = new Printable[Person] {
    override def format(person: Person): String = s"${person.first} ${person.last}"
  }

  implicit val personEq = Eq.instance[Person] { (p1, p2) =>
    p1.first == p2.first && p1.last == p2.last
  }

  val andrei = Person("Andrei", "Fecioru")
  val john = Person("John", "Doe")
  Printable.print(andrei)
  john.print

  println(s"Andrei is equal to John? ${Eq.eqv(andrei, john)}")
}