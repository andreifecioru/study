package org.afecioru.study
package ch_04
package ex_4_3_6_2

package polymorphic {
  sealed trait Tree {
    def left: Tree
    def right: Tree
    def sum: Int
    def double: Tree
  }

  final case class Leaf(value: Int) extends Tree {
    override def left: Tree = throw new NoSuchElementException("Leaf nodes don't have children")
    override def right: Tree = throw new NoSuchElementException("Leaf nodes don't have children")
    override def sum: Int = value
    override def double: Tree = Leaf(value * 2)
  }

  final case class Node(value: Int, left: Tree, right: Tree) extends Tree {
    override def sum: Int = value + left.sum + right.sum
    override def double: Tree = Node(value * 2, left.double, right.double)
  }


  class Ex_4_6_3_2 extends BaseTestSuite {
    val testData: Tree = Node(1,
      Node(2,
        Leaf(4),
        Leaf(5)
      ),
      Leaf(3)
    )

    test("sum works") {
      testData.sum mustBe 15
      testData.left.sum mustBe 11
      testData.left.left.sum mustBe 4
    }

    test("double works") {
      testData.double mustBe Node(2,
        Node(4,
          Leaf(8),
          Leaf(10)
        ),
        Leaf(6)
      )
    }
  }

}

package pattern_matching {
  sealed trait Tree
  sealed case class Leaf(value: Int) extends Tree
  sealed case class Node(value: Int, left: Tree, right: Tree) extends Tree

  object TreeOps {
    def left(tree: Tree): Tree = tree match {
      case Leaf(_) => throw new NoSuchElementException("leaf nodes don't have children")
      case Node(_, left, _) => left
    }

    def right(tree: Tree): Tree = tree match {
      case Leaf(_) => throw new NoSuchElementException("leaf nodes don't have children")
      case Node(_, _, right) => right
    }

    def sum(tree: Tree): Int = tree match {
      case Leaf(value) => value
      case Node(value, left, right) => value + sum(left) + sum(right)
    }

    def double(tree: Tree): Tree = tree match {
      case Leaf(value) => Leaf(value * 2)
      case Node(value, left, right) => Node(value * 2, double(left), double(right))
    }
  }

  class Ex_4_6_3_2 extends BaseTestSuite {
    import TreeOps._
    val testData: Tree = Node(1,
      Node(2,
        Leaf(4),
        Leaf(5)
      ),
      Leaf(3)
    )

    test("sum works") {
      sum(testData) mustBe 15
      sum(left(testData)) mustBe 11
      sum(left(left(testData))) mustBe 4
    }

    test("double works") {
      double(testData) mustBe Node(2,
        Node(4,
          Leaf(8),
          Leaf(10)
        ),
        Leaf(6)
      )
    }
  }
}

