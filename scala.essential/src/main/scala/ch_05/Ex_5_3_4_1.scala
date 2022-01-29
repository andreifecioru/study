package org.afecioru.study
package ch_05
package ex_5_3_4_1

sealed trait Tree[T] {
  def left: Tree[T]
  def right: Tree[T]

  def fold[U](zero: U)(f: (U, T) => U): U = this match {
    case Leaf(value) => f(zero, value)
    case Node(left, right) =>
      val leftFolded = left.fold(zero)(f)
      right.fold(leftFolded)(f)
  }
}

final case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
final case class Leaf[T](value: T) extends Tree[T] {
  override def left: Tree[T] = ???
  override def right: Tree[T] = ???
}


class Ex_5_3_4_1 extends BaseTestSuite {
  val testData: Tree[String] = Node(
    Node(Leaf("To"), Leaf("iterate")),
    Node(Node(Leaf("is"), Leaf("human")),
         Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine"))))
  )

  test("fold works") {
    val asString = testData.fold(""){ (acc, value) => if (acc.isEmpty) value else s"$acc $value" }
    asString mustBe "To iterate is human to recurse divine"
  }
}
