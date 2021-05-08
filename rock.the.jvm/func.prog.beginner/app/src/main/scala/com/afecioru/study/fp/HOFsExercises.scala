package com.afecioru.study.fp

import scala.annotation.tailrec

//noinspection DuplicatedCode
object HOFsExercises extends App {

  object MyList {
    def empty[T]: MyList[T] = Empty

    def fromRange(range: Range): MyList[Int] =
      fromValues(range:_*)

    def fromValues[T](values: T*): MyList[T] =
      values.foldLeft(MyList.empty[T])(_ add _).reverse()

    def fill[T](value: T, times: Int): MyList[T] =
      if (times == 0) MyList.empty[T]
      else Node(value, fill(value, times - 1))

    @tailrec
    private def concat_helper[U](l1:MyList[U], l2: MyList[U]): MyList[U] = {
      if (l2.isEmpty) l1
      else if (l1.isEmpty) l2
      else concat_helper(l1.tail, Node(l1.head, l2))
    }
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

    def filter(predicate: T => Boolean): MyList[T]

    def map[U](transformer: T => U): MyList[U]

    def flatMap[U](myTransformer: T => MyList[U]): MyList[U]

    def foreach(action: T => Unit): Unit

    def sort(sortBy: (T, T) => Int): MyList[T]

    def zipWith[U, R](other: MyList[U], combiner: (T, U) => R): MyList[R]

    def fold[U](start: U)(combiner: (U, T) => U): U

    protected[HOFsExercises] def elementsAsString: String

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

    override def filter(predicate: Nothing => Boolean): MyList[Nothing] = MyList.empty[Nothing]

    def map[U](transformer: Nothing => U): MyList[U] = MyList.empty[U]

    def flatMap[U](myTransformer: Nothing => MyList[U]): MyList[U] = MyList.empty[U]

    def foreach(action: Nothing => Unit): Unit = ()

    def sort(sortBy: (Nothing, Nothing) => Int): MyList[Nothing] = MyList.empty[Nothing]

    def zipWith[U, R](other: MyList[U], combiner: (Nothing, U) => R): MyList[R] = MyList.empty[R]

    def fold[U](start: U)(combiner: (U, Nothing) => U): U = start

    // no need to specify a lower type bound for T because Nothing is the bottom-most type
    override def add[T](value: T): MyList[T] = Node(value, Empty)

    override protected[HOFsExercises] def elementsAsString: String = ""
  }

  case class Node[+T](
    override val head: T,
    override val tail: MyList[T]
  ) extends MyList[T] {
    override def isEmpty: Boolean = false

    override def add[U >: T](value: U): MyList[U] = Node(value, this)

    override def copy(): MyList[T] = Node(head, tail.copy())

    override def reverse(): MyList[T] = {
      @tailrec
      def _reverse(l: MyList[T], r: MyList[T] = MyList.empty[T]): MyList[T] =
        if (l.isEmpty) r
        else _reverse(l.tail, Node(l.head, r))

      _reverse(this)
    }

    override protected[HOFsExercises] def elementsAsString: String =
      if (tail.isEmpty) s"$head"
      else s"$head, ${tail.elementsAsString}"

    override def filter(predicate: T => Boolean): MyList[T] =
      if (predicate(head)) Node(head, tail.filter(predicate))
      else tail.filter(predicate)

    override def map[U](transformer: T => U): MyList[U] =
      Node(transformer(head), tail.map(transformer))

    def flatMap[U](transformer: T => MyList[U]): MyList[U] = {
      transformer(head).concat(tail.flatMap(transformer))
    }

    def foreach(action: T => Unit): Unit = {
      action(head)
      if (!tail.isEmpty) tail.foreach(action)
    }

    def sort(sortBy: (T, T) => Int): MyList[T] = {
      def _insert(value: T, l: MyList[T]): MyList[T] = {
        if (l.isEmpty) Node(value, Empty)
        else if (sortBy(value, l.head) <= 0) Node(value, l)
        else Node(l.head, _insert(value, l.tail))
      }

      @tailrec
      def _sort(l: MyList[T], r: MyList[T]): MyList[T] = {
        if (l.isEmpty) r
        else _sort(l.tail, _insert(l.head, r))
      }

      _sort(tail, Node(head, Empty))
    }

    def zipWith[U, R](other: MyList[U], combiner: (T, U) => R): MyList[R] = {
      @tailrec
      def _combine(l: MyList[T], r: MyList[U], acc: MyList[R] = MyList.empty): MyList[R] =
        if (l.isEmpty || r.isEmpty) acc
        else _combine(l.tail, r.tail, Node(combiner(l.head, r.head), acc))

      _combine(this, other)
    }

    def fold[U](start: U)(combiner: (U, T) => U): U = {
      @tailrec
      def _helper(l: MyList[T], acc: U = start): U = {
        if (l.isEmpty) acc
        else _helper(l.tail, combiner(acc, l.head))
      }

      _helper(this)
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

  println(aList.filter(_ % 2 == 0))

  println(aList.map(item => s"-$item-"))

  println(aList.flatMap(MyList.fill(_, 3)))

  val listOfValues = MyList.fromValues(2, 5, 1, 10, 8, 6, 3)
  listOfValues.foreach(println)

  val sortedValues = listOfValues.sort(_ - _)
  println(sortedValues)

  // we have to help the compiler a bit by specifying type params for the `zipWith` method
  val zippedValues = sortedValues.zipWith[Int, Int](MyList.fill(3, 3), _ + _)
  println(zippedValues)
  println(zippedValues.fold(0)(_ + _))

  println("\n\n---------------------------------------\n\n")

  def toCurry[T](f: (T, T) => T): T => T => T = (x: T) => (y: T) => f(x, y)
  def fromCurry[T](f: T => T => T): (T, T) => T = (x: T, y: T) => f(x)(y)

  def add(x: Int, y: Int): Int = x + y
  val curriedAdd = toCurry(add)
  println(curriedAdd(2)(3))
  println(fromCurry(curriedAdd)(2, 3))

  println("\n\n---------------------------------------\n\n")

  // These are also available as part of the API exposed by the FunctionX family of traits
  def compose[T1, T2, T3](f: T2 => T3)(g: T1 => T2): T1 => T3 = x => f(g(x))
  def andThen[T1, T2, T3](f: T1 => T2)(g: T2 => T3): T1 => T3 = x => g(f(x))

  println(compose[Int, String, Int](_.length)(_.toString)(1234))
  println(andThen[Int, String, Int](_.toString)(_.length)(1234))

}
