package org.afecioru.study
package ch_05
package ex_5_2_3_2

import scala.annotation.tailrec

sealed trait Result[T]
final case class Success[T](value: T) extends Result[T]
final case class Failure[T](reason: String) extends Result[T]


sealed trait LinkedList[T] {
  final def length: Int = this match {
    case ListEnd() => 0
    case ListNode(_, tail) => 1 + tail.length
  }

  @tailrec
  final def contains(value: T): Boolean = this match {
    case ListEnd() => false
    case ListNode(head, tail) => if (value == head) true else tail.contains(value)
  }

  final def apply(index: Int): Result[T] = this match {
    case ListEnd() => Failure("Index out of bounds")
    case ListNode(head, _) if index == 0 => Success(head)
    case ListNode(head, tail) => tail(index - 1)
  }
}
final case class ListNode[T](head: T, tail: LinkedList[T]) extends LinkedList[T]
final case class ListEnd[T]() extends LinkedList[T]


//noinspection DuplicatedCode
class Ex_5_1_3_2 extends BaseTestSuite {
  val testData: ListNode[Int] = ListNode(1, ListNode(2, ListNode(3, ListEnd())))

  test("length works") {
    testData.length mustBe 3
    testData.tail.length mustBe 2
    ListEnd().length mustBe 0
  }

  test("contains works") {
    testData.contains(3) mustBe true
    testData.contains(4) mustBe false
    testData.contains(0) mustBe false

    """testData.contains("not an Int")""" mustNot compile
  }

  test("indexing works") {
    testData(0) mustBe Success(1)
    testData(1) mustBe Success(2)
    testData(2) mustBe Success(3)
    testData(3) mustBe Failure("Index out of bounds")
  }
}
