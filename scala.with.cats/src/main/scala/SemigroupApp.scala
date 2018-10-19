import fun.Semigroup
import fun.SemigroupInstances._
import model.Person

object SemigroupApp extends App {
  val combinedInts = Semigroup.combine(1, 2)
  println(combinedInts)

  val combinedListOfInts = Semigroup.combine(List(1, 2), List(3, 4))
  println(combinedListOfInts)

  val personListOfPersons = Semigroup.combine(
    List(Person("John", "Doe")),
    List(Person("Jane", "Doe"))
  )
  println(personListOfPersons)
}
