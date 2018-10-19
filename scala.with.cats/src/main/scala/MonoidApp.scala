import fun.Monoid
import fun.MonoidInstances._
import model.Person

object MonoidApp extends App {
  val combinedInts = Monoid.combine(1, 3)
  println(combinedInts)

  val combinedListOfInts = Monoid.combine(List(1, 2, 3), List(4))
  println(combinedListOfInts)

  val personListOfPersons = Monoid.combine(
    List(Person("John", "Doe")),
    List(Person("Jane", "Doe"))
  )
  println(personListOfPersons)

}
