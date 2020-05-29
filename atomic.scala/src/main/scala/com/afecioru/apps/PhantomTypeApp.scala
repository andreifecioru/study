package com.afecioru.apps

import scala.collection.mutable.ArrayBuffer

object PhantomTypeApp extends App {
  class Item[T <: Box](private val value: Int) {
    // T is the phantom type here
    // there is no instance of T created
    // T acts like a type annotation
    def show(): Unit = {
      println(s"Value is: $value")
    }
  }

  class Box {
    // restrict the items to items generated via this Box instance only
    val items: ArrayBuffer[Item[this.type]] = ArrayBuffer.empty

    def addValue(value: Int): Unit = items += new Item(value)
    def addItem(item: Item[this.type]): Unit = items += item
    def firstItem(): Item[this.type] = items.head
    def lookInside(): Unit = {
      items.foreach(_.show())
    }
  }

  val boxOne = new Box()
  boxOne.addValue(5)
  boxOne.addValue(10)
  boxOne.lookInside()

  val boxTwo = new Box()
  boxTwo.addValue(5)
  boxTwo.addValue(10)
  boxTwo.lookInside()

  // we cannot place items from `boxOne` into `boxTwo`
  //boxTwo.addItem(boxOne.firstItem)
}
