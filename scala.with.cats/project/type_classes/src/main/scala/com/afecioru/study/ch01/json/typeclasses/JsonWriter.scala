package com.afecioru.study.ch01.json.typeclasses

import com.afecioru.study.ch01.json.models.Json

trait JsonWriter[A] {
  def write(input: A): Json
}
