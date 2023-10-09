package com.afecioru.study.ch01.json.models

import com.afecioru.study.ch01.json.typeclasses.JsonWriter

sealed trait Json

case object JsonNull extends Json
case class JsonObject(get: Map[String, Json]) extends Json
case class JsonValue(get: String) extends Json

// Interface object
object Json {
  def toJson[A](input: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(input)
}

// Interface syntax
object JsonSyntax {
  implicit class JsonWriterExtension[A](input: A) {
    def toJson(implicit writer: JsonWriter[A]): Json = {
      writer.write(input)
    }
  }
}
