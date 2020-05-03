package com.afecioru.apps

import scala.collection.mutable.ArrayBuffer

abstract class Queue {
  def pop(): Int
  def put(value: Int): Unit
}

// base class with default implementation of `put`
class FifoQueue extends Queue {
  private val buffer = new ArrayBuffer[Int]()
  def pop(): Int = buffer.remove(0)
  def put(value: Int): Unit = buffer += value

  override def toString: String = buffer mkString ", "
}

// trait that can only be mixed into `Queue` classes
// (or its subclasses)
trait Incrementing extends Queue {
  // customize `put`: define it as `abstract override`
  // works because it will be mixed into a class that provides
  // a concrete implementation of `put()`
  abstract override def put(value: Int): Unit = super.put(value + 1)
}

// another similar trait (ignore all non-negative values)
trait FilterPositive extends Queue {
  abstract override def put(value: Int): Unit = if (value >= 0) super.put(value)
}

// stacking traits (order matters: most-right trait is considered first)
class MyQueue1 extends FifoQueue with Incrementing with FilterPositive
class MyQueue2 extends FifoQueue with FilterPositive with Incrementing


object StackModsApp extends App {
  // filtering is applied 1st
  val q1 = new MyQueue1
  q1.put(-1)
  q1.put(1)
  q1.put(2)
  println(q1) // 2, 3

  // incrementing is applied first
  val q2 = new MyQueue2
  q2.put(-1)
  q2.put(1)
  q2.put(2)
  println(q2) // 0, 2, 3

}
