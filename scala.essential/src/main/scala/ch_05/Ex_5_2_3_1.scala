package org.afecioru.study
package ch_05
package ex_5_3_2_1

import scala.annotation.tailrec

package take_one {
  //noinspection DuplicatedCode
  sealed trait IntList {
    def head: Int
    def tail: IntList

    @tailrec
    final def fold[U](zero: U)(f: (U, Int) => U): U = this match {
      case ListEnd() => zero
      case ListNode(head, tail) => tail.fold(f(zero, head))(f)
    }

    final def length: Int = fold(0)((acc, _) => acc + 1)
    final def reverse: IntList = fold[IntList](ListEnd())((acc, head) => ListNode(head, acc))
    final def sum: Int = fold(0)(_ + _)
    final def product: Int = fold(1)(_ * _)
    final def double: IntList = fold[IntList](ListEnd())((acc, head) => ListNode(head * 2, acc)).reverse
  }
  final case class ListNode(head: Int, tail: IntList) extends IntList
  final case class ListEnd() extends IntList {
    override def head: Int = ???
    override def tail: IntList = ???
  }


  //noinspection DuplicatedCode
  class Ex_5_2_3_1 extends BaseTestSuite {
    val testData: ListNode = ListNode(1, ListNode(2, ListNode(3, ListEnd())))

    test("length works") {
      testData.length mustBe 3
      testData.tail.length mustBe 2
      ListEnd().length mustBe 0
    }

    test("sum works") {
      testData.sum mustBe 6
      testData.tail.sum mustBe 5
      ListEnd().sum mustBe 0
    }

    test("product works") {
      testData.product mustBe 6
      testData.tail.product mustBe 6
      ListEnd().product mustBe 1
    }

    test("double works") {
      testData.double mustBe ListNode(2, ListNode(4, ListNode(6, ListEnd())))
      ListEnd().double mustBe ListEnd()
    }
  }
}

package take_two {
  //noinspection DuplicatedCode
  sealed trait LinkedList[T] {
    def head: T
    def tail: LinkedList[T]

    @tailrec
    final def fold[U](zero: U)(f: (U, T) => U): U = this match {
      case ListEnd() => zero
      case ListNode(head, tail) => tail.fold(f(zero, head))(f)
    }

    final def length: Int = fold(0)((acc, _) => acc + 1)
    final def reverse: LinkedList[T] = fold[LinkedList[T]](ListEnd())((acc, head) => ListNode(head, acc))
  }
  final case class ListNode[T](head: T, tail: LinkedList[T]) extends LinkedList[T]
  final case class ListEnd[T]() extends LinkedList[T] {
    override def head: T = ???
    override def tail: LinkedList[T] = ???
  }

  object implicits {
    implicit class IntListOps(intList: LinkedList[Int]) {
      def sum: Int = intList.fold(0)(_ + _)
      def product: Int = intList.fold(1)(_ * _)
      def double: LinkedList[Int] = intList.fold[LinkedList[Int]](ListEnd())((acc, head) => ListNode(head * 2, acc)).reverse
    }
  }

  //noinspection DuplicatedCode
  class Ex_5_2_3_1 extends BaseTestSuite {
    import implicits.IntListOps

    val testData: ListNode[Int] = ListNode(1, ListNode(2, ListNode(3, ListEnd())))

    test("length works") {
      testData.length mustBe 3
      testData.tail.length mustBe 2
      ListEnd().length mustBe 0
    }

    test("sum works") {
      testData.sum mustBe 6
      testData.tail.sum mustBe 5
      ListEnd[Int]().sum mustBe 0
    }

    test("double works") {
      testData.double mustBe ListNode(2, ListNode(4, ListNode(6, ListEnd())))
      ListEnd[Int]().double mustBe ListEnd()
    }
  }
}

