import model.Person

import exercises.Printable
import exercises.PrintableInstances._
import exercises.PrintableSyntax._


object PrintableApp extends App {
  Printable.print(5)
  Printable.print(false)
  Printable.print(Option(5))
  Printable.print(Option.empty[Int])
  Printable.print(Seq(1, 2, 3))

  implicit val personPrintable: Printable[Person] = new Printable[Person] {
    override def format(person: Person): String = s"${person.first} ${person.last}"
  }

  Printable.print(Person("Andrei", "Fecioru"))
  Person("John", "Doe").print
}