package com.afecioru.study.ch01.json.models

final case class Person(
  firstName: String,
  lastName: String,
  age: Int,
  email: Option[String] = None
)
