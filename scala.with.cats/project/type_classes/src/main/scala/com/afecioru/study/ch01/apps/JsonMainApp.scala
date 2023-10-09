package com.afecioru.study.ch01.apps

import com.afecioru.study.ch01.json.models.{Json, JsonSyntax, Person}

object JsonMainApp extends App {
  import com.afecioru.study.ch01.json.typeclasses.JsonFormats._
  import JsonSyntax._

  private val andrei = Person("Andrei", "Fecioru", 42)
  println(Json.toJson(andrei))

  private val john = Person("John", "Smith", 40, Some("john.smith@email.com"))
  println(john.toJson)
}
