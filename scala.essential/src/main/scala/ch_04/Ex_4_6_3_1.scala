package org.afecioru.study
package ch_04
package ex_4_6_3_1

import scala.annotation.tailrec

sealed trait IntList {
  def length: Int
  def product: Int
  def double: IntList
}

case object End extends IntList {
  override def length: Int = 0
  override def product: Int = 1
  override def double: IntList = End
}

final case class Pair(head: Int, tail: IntList) extends IntList {
  @tailrec
  private[this] def _fold(pair: IntList, acc: Int)(op: (Int, Int) => Int): Int = {
    pair match {
      case End => acc
      case Pair(head, tail) => _fold(tail, op(acc, head))(op)
    }
  }

  private[this] def _map(pair: IntList)(op: Int => Int): IntList = {
    pair match {
      case End => End
      case Pair(head, tail) => Pair(op(head), _map(tail)(op))
    }
  }

  override def length: Int =
    _fold(this, acc = 0)((acc, _) => acc + 1)

  override def product: Int = {
    _fold(this, acc = 1)((acc, head) => acc * head)
  }

  override def double: IntList =
    _map(this)(_ * 2)
}


class Ex_4_6_3_1 extends BaseTestSuite {
  val testData = Pair(1, Pair(2, Pair(3, End)))

  test("length works") {
    testData.length mustBe 3
    testData.tail.length mustBe 2
    End.length mustBe 0
  }

  test("product works") {
    testData.product mustBe 6
    testData.tail.product mustBe 6
    End.product mustBe 1
  }

  test("double works") {
    testData.double mustBe Pair(2, Pair(4, Pair(6, End)))
    testData.tail.double mustBe Pair(4, Pair(6, End))
    End.double mustBe End
  }

}
