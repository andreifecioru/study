package com.afecioru.study.oop

import scala.annotation.tailrec

object Generics extends App {

  object MyList {
    def empty[T]: MyList[T] = Empty

    def fromRange(range: Range): MyList[Int] =
      range.foldLeft(MyList.empty[Int])(_ add _).reverse()

    def fill[T](value: T, times: Int): MyList[T] =
      if (times == 0) MyList.empty[T]
      else new Node(value, fill(value, times - 1))

    @tailrec
    private def concat_helper[U](l1:MyList[U], l2: MyList[U]): MyList[U] = {
      if (l2.isEmpty) l1
      else if (l1.isEmpty) l2
      else concat_helper(l1.tail, new Node(l1.head, l2))
    }
  }

  trait MyPredicate[-T] {
    def test(item: T): Boolean
  }

  trait MyTransformer[-T, +U] {
    def apply(item: T): U
  }

  sealed abstract class MyList[+T] {
    import MyList._

    def head: T
    def tail: MyList[T]
    def isEmpty: Boolean
    def add[U >: T](value: U): MyList[U]

    def copy(): MyList[T]

    def reverse(): MyList[T]

    def concat[U >: T](other: MyList[U]): MyList[U] =
      concat_helper(this.reverse(), other)

    def filter(predicate: MyPredicate[T]): MyList[T]

    def map[U](transformer: MyTransformer[T, U]): MyList[U]

    def flatMap[U](myTransformer: MyTransformer[T, MyList[U]]): MyList[U]

    protected[Generics] def elementsAsString: String

    override def toString: String = s"[$elementsAsString]"
  }

  case object Empty extends MyList[Nothing] {
    override def head: Nothing = throw new NoSuchElementException
    override def tail: MyList[Nothing] = throw new NoSuchElementException
    override def isEmpty: Boolean = true

    override def copy(): MyList[Nothing] = MyList.empty[Nothing]

    override def reverse(): MyList[Nothing] = MyList.empty[Nothing]

    // no need to specify a lower type bound for T because Nothing is the bottom-most type
    override def concat[U](other: MyList[U]): MyList[U] = other

    override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = MyList.empty[Nothing]

    def map[U](transformer: MyTransformer[Nothing, U]): MyList[U] = MyList.empty[U]

    def flatMap[U](myTransformer: MyTransformer[Nothing, MyList[U]]): MyList[U] = MyList.empty[U]

    // no need to specify a lower type bound for T because Nothing is the bottom-most type
    override def add[T](value: T): MyList[T] = new Node(value, Empty)

    override protected[Generics] def elementsAsString: String = ""
  }

  case class Node[+T](
    override val head: T,
    override val tail: MyList[T]
  ) extends MyList[T] {
    override def isEmpty: Boolean = false

    override def add[U >: T](value: U): MyList[U] = new Node[U](value, this)

    override def copy(): MyList[T] = new Node(head, tail.copy())

    override def reverse(): MyList[T] = {
      @tailrec
      def _reverse(l: MyList[T], r: MyList[T] = MyList.empty[T]): MyList[T] =
        if (l.isEmpty) r
        else _reverse(l.tail, new Node(l.head, r))

      _reverse(this)
    }

    override protected[Generics] def elementsAsString: String =
      if (tail.isEmpty) s"$head"
      else s"$head, ${tail.elementsAsString}"

    override def filter(predicate: MyPredicate[T]): MyList[T] =
      if (predicate.test(head)) new Node(head, tail.filter(predicate))
      else tail.filter(predicate)

    override def map[U](transformer: MyTransformer[T, U]): MyList[U] =
      new Node[U](transformer.apply(head), tail.map(transformer))

    def flatMap[U](transformer: MyTransformer[T, MyList[U]]): MyList[U] = {
      transformer(head).concat(tail.flatMap(transformer))
    }
  }

  val aList = MyList.fromRange(1 to 10)
  println(aList)
  println(aList.copy())
  println(aList.reverse())

  val anotherList = MyList.fromRange(100 to 105)
  println(anotherList)

  println(aList concat anotherList)

  println("\n\n---------------------------------------\n\n")

  val isEven = new MyPredicate[Int] {
    override def test(item: Int): Boolean = item % 2 == 0
  }
  println(aList.filter(isEven))

  val transformerMap = new MyTransformer[Int, String] {
    override def apply(item: Int): String = s"-$item-"
  }
  println(aList.map(transformerMap))

  val transformerFlatMap = new MyTransformer[Int, MyList[Int]] {
    override def apply(item: Int): MyList[Int] = MyList.fill(item, 3)
  }
  println(aList.flatMap(transformerFlatMap))
}
