package com.afecioru.study.ch01.json.typeclasses

import com.afecioru.study.ch01.json.models._

object JsonFormats {
  import com.afecioru.study.ch01.json.models.JsonSyntax._

  implicit val intJsonWriter: JsonWriter[Int] =
    (input: Int) => JsonValue(input.toString)

  implicit val stringJsonWriter: JsonWriter[String] =
    (input: String) => JsonValue(input)

  implicit val personJsonWriter: JsonWriter[Person] =
    (person: Person) => JsonObject(Map(
      "firstName" -> JsonValue(person.firstName),
      "lastName" -> JsonValue(person.lastName),
      "age" -> Json.toJson(person.age),
      "email" -> person.email.toJson
    ))

  // Type-class composition via implicit type-class constructors
  //   - this converts a JsonWriter[A] into JsonWriter[Option[A]]
  implicit def optionJsonWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = {
    case Some(value) => writer.write(value)
    case None => JsonNull
  }
}
