import fun.Functor
import fun.FunctorInstances._
import model.Box

object FunctorApp extends App {
  val box = Box("Andrei")

  val result = Functor[Box].map(box)(_.length)

  System.out.println(s"Result: $result")

}
