package org.afecioru.study
package ch_04
package ex_4_7_0_2

import scala.annotation.tailrec

sealed trait JsonValue {
  def print: String = {
    def addComma(in: String): String =
      if (Seq(", ", "[ ", "{ ").exists(in.endsWith)) in else s"$in,"

    @tailrec
    def _print(node: JsonValue, acc: String = "", openBracket: Option[Char] = None): String = {
      node match {
        case JsonString(value) =>
          s"$acc\"$value\""
        case JsonBool(value) =>
          s"$acc$value"
        case JsonNumber(value) =>
          s"$acc$value"

        case JsonSeqCell(head, tail) if openBracket.isEmpty =>
          _print(tail, acc = s"$acc[${head.print}, ", openBracket = Some('['))
        case JsonSeqCell(head, JsonSeqEnd) if openBracket.getOrElse("") == '[' =>
          _print(JsonSeqEnd, acc = s"$acc${head.print}", openBracket = Some('['))
        case JsonSeqCell(head, tail) if openBracket.getOrElse("") == '[' =>
          _print(tail, acc = s"$acc${head.print}, ", openBracket = Some('['))
        case JsonSeqEnd if openBracket.getOrElse("") == '[' =>
          s"$acc]"
        case JsonSeqEnd if openBracket.isEmpty =>
          s"$acc[]"

        case JsonObjectCell((key, value), tail) if openBracket.isEmpty =>
          _print(tail, acc = s"$acc{\"$key\": ${value.print}, ", openBracket = Some('{'))
        case JsonObjectCell((key, value), JsonObjectEnd) if openBracket.getOrElse("") == '{' =>
          _print(JsonObjectEnd, acc = s"$acc\"$key\": ${value.print}", openBracket = Some('{'))
        case JsonObjectCell((key, value), tail) if openBracket.getOrElse("") == '{' =>
          _print(tail, acc = s"$acc\"$key\": ${value.print}, ", openBracket = Some('{'))
        case JsonObjectEnd if openBracket.getOrElse("") == '{' =>
          s"$acc}"
        case JsonObjectEnd if openBracket.isEmpty =>
          s"$acc{}"

        case _ =>
          "Should not get here"
      }
    }

    _print(this)
  }
}
final case class JsonString(value: String) extends JsonValue
final case class JsonBool(value: Boolean) extends JsonValue
final case class JsonNumber(value: Double) extends JsonValue

sealed trait JsonSeq extends JsonValue {
  def head: JsonValue
  def tail: JsonSeq
}
case object JsonSeqEnd extends JsonSeq {
  override def head: JsonValue = throw new NoSuchElementException
  override def tail: JsonSeq = throw new NoSuchElementException
}
case class JsonSeqCell(head: JsonValue, tail: JsonSeq) extends JsonSeq

sealed trait JsonObject extends JsonValue {
  def head: (String, JsonValue)
  def tail: JsonObject
}
case object JsonObjectEnd extends JsonObject {
  override def head: (String, JsonValue) = throw new NoSuchElementException
  def tail: JsonObject = throw new NoSuchElementException
}

case class JsonObjectCell(head: (String, JsonValue), tail: JsonObject) extends JsonObject

class Ex_4_7_0_2 extends BaseTestSuite {

  test("empty array") {
    val asString = JsonSeqEnd.print

    asString mustBe "[]"
  }

  test("simple array") {
    val asString = JsonSeqCell(JsonString("a string"),
      JsonSeqCell(JsonNumber(1.0),
        JsonSeqCell(JsonBool(true),
          JsonSeqEnd)))
      .print

    asString mustBe """["a string", 1.0, true]"""
  }

  test("nested array") {
    val asString = JsonSeqCell(JsonString("a string"),
        JsonSeqCell(JsonNumber(1.0),
          JsonSeqCell(JsonSeqCell(JsonBool(true), JsonSeqCell(JsonString("more"), JsonSeqEnd)),
            JsonSeqEnd)))
      .print

    asString mustBe "[\"a string\", 1.0, [true, \"more\"]]"
  }

  test("empty object") {
    val asString = JsonObjectEnd.print
    asString mustBe "{}"
  }

  test("simple object") {
    val asString = JsonObjectCell("doh" -> JsonBool(true),
      JsonObjectCell("ray" -> JsonBool(false),
        JsonObjectCell("me" -> JsonNumber(1.0), JsonObjectEnd))).print

    asString mustBe  "{\"doh\": true, \"ray\": false, \"me\": 1.0}"
  }

  test("mixed and nested") {
    val asString =
      JsonObjectCell(
        "a" -> JsonSeqCell(JsonNumber(1.0), JsonSeqCell(JsonNumber(2.0), JsonSeqCell(JsonNumber(3.0), JsonSeqEnd))),
        JsonObjectCell(
          "b" -> JsonSeqCell(JsonString("a"), JsonSeqCell(JsonString("b"), JsonSeqCell(JsonString("c"), JsonSeqEnd))),
          JsonObjectCell(
            "c" -> JsonObjectCell("doh" -> JsonBool(true),
                     JsonObjectCell("ray" -> JsonBool(false),
                       JsonObjectCell("me" -> JsonNumber(1.0), JsonObjectEnd))),
            JsonObjectEnd
          )
        )
      ).print

    asString mustBe "{\"a\": [1.0, 2.0, 3.0], \"b\": [\"a\", \"b\", \"c\"], \"c\": {\"doh\": true, \"ray\": false, \"me\": 1.0}}"
  }
}
