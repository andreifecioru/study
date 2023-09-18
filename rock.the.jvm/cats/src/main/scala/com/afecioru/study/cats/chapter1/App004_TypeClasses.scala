package com.afecioru.study.cats.chapter1

object App004_TypeClasses extends App {

  case class Person(name: String, age: Int)

  // PART 1: type class definition
  // Usually a generic trait
  trait JsonSerializer[T] {
    def toJson(value: T): String
  }

  object JsonInstances {
    // PART 2: type class instances
    // Implicitly defined concrete instances for the type class
    implicit object StringSerializer extends JsonSerializer[String] {
      override def toJson(value: String): String = "\"" + value + "\""
    }

    implicit object IntSerializer extends JsonSerializer[Int] {
      override def toJson(value: Int): String = s"$value"
    }

    implicit object PersonSerializer extends JsonSerializer[Person] {
      override def toJson(person: Person): String =
        s"""
           |{"name": "${person.name}", "age": ${person.age}}
           |""".stripMargin.trim
    }
  }

  // PART 3: usage API
  def convertListToJson[T](list: List[T])
                          (implicit serializer: JsonSerializer[T]): String = {
    list.map(serializer.toJson).mkString("[", ", ", "]")
  }


  // PART 4: extend existing types
  object JsonSyntax {
    implicit class JsonSerializable[T](val value: T)
                                      (implicit serializer: JsonSerializer[T]) {
      def toJson: String =
        serializer.toJson(value)
    }

    implicit class SeqJsonSerializable[T](val seq: List[T])
                                         (implicit serializer: JsonSerializer[T]) {
      def toJson: String =
        seq.map(serializer.toJson).mkString("[", ", ", "]")
    }
  }

  val persons = List(
    Person("Andrei", 42),
    Person("John", 55)
  )

  import JsonInstances._
  import JsonSyntax._

  println(convertListToJson(persons))
  println(Person("Jane", 44).toJson)
  println(persons.toJson)
}
