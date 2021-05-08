package com.afecioru.study.oop

object MyListApp extends App {

  sealed abstract class MyList {
    def head: Int
    def tail: MyList
    def isEmpty: Boolean
    def add(value: Int): MyList

    protected[MyListApp] def elementsAsString: String

    override def toString: String = {
      s"[${elementsAsString}]"
    }

  }

  class ListNode(
    override val head: Int,
    override val tail: MyList
  ) extends MyList {
    override val isEmpty: Boolean = false

    override def add(value: Int): MyList = new ListNode(value, this)

    override def elementsAsString: String = s"$head, ${tail.elementsAsString}"
  }

  // the empty-list is a singleton
  object EmptyList extends MyList {
    override def head: Int = throw new NoSuchElementException
    override def tail: MyList = throw new NoSuchElementException

    override val isEmpty: Boolean = true  // def in the base class can be overridden by val in child class

    override def add(value: Int): MyList = new ListNode(value, EmptyList)

    override def elementsAsString: String = ""
  }

  val aList = (1 to 10).foldLeft(EmptyList: MyList /* we need to provide the type here */)(_ add _)
  println(aList)
}
