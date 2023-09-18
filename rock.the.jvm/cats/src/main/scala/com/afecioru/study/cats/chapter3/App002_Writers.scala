package com.afecioru.study.cats.chapter3

import scala.annotation.tailrec

object App002_Writers extends App {

  /*
    Writers help you keep track of changes that you apply to some piece of data that you manipulate.
    You can implement a "log of changes" for the data of interest.

    The Writer[L, V] type has two type parameters:
     - the "logs" type param accumulates the changes
     - the "value" type param is the type of the data of interest.

    Example: data of interest is an int; and we want to also keep track of the changes in the
    form of tuples (oldValue -> newValue).

    NOTE: for logs we usually use Vector since it's faster for append and combine ops

    Benefits:
      #1 - a pure FP way of dealing with the "side-effects" of tracking state changes
      #2 - if called from separate threads, the logs will be kept separately for each thread
  */

  import cats.data.Writer
  val changeLogWriter: Writer[List[(Int, Int)], Int] = Writer(List.empty, 40)

  // modify the value w/o changing the logs
  val modifyValueWriter = changeLogWriter.map(_ + 1) // bump the value to 41
  // modify the logs w/o changing the value
  val modifyLogsWriter = modifyValueWriter.mapWritten(_ :+ 40 -> 41) // add the previous value change to the change log

  // modify both value and logs
  val modifyBothWriter = modifyLogsWriter.bimap(
    _ :+ 41 -> 42, // modify logs
    _ + 1          // modify value
  )
  // with this variant you have access to both the logs and the value in the same function
  val anotherModifyBothWriter = modifyBothWriter.mapBoth { (logs, value) =>
    val newValue = value + 1
    (logs :+ value -> newValue) -> newValue
  }
  // reset the logs, but keep the value (we need an empty value for the logs type => we need a Monoid[List]
  import cats.instances.list._ // adds Monoid[List] to scope
  val resetWriter = anotherModifyBothWriter.reset

  // access the value
  val theValue = anotherModifyBothWriter.value
  // access the logs
  val theLogs = anotherModifyBothWriter.written
  // access both the value and the logs
  val (anotherValue, moreLogs) = anotherModifyBothWriter.run

  println("---------[ OUTPUT 2 ]---------")
  println(anotherModifyBothWriter)
  println(resetWriter)
  println("------------------------------")

  /*
    COMPOSITION: writers can be flatMap => they can be used in for-comp

    You define how the values are combined, and for logs composition, there needs to be
    a Semigroup in scope of the logs type.
  */
  import cats.instances.vector._ // adds Semigroup[Vector] to scope

  val writerA = Writer(Vector("A1", "A2"), 10)
  val writerB = Writer(Vector("B1"), 20)

  val composedWriter = for {
    valueA <- writerA
    valueB <- writerB
  } yield valueA + valueB

  println("---------[ OUTPUT 3 ]---------")
  println(composedWriter)
  println("------------------------------")

  // Exercise 1: track values in a counter
  def countAndSay(n: Int): Unit = {
    if (n <= 0) println("starting")
    else {
      countAndSay(n - 1)
      println(n)
    }
  }

  println("---------[ OUTPUT 4 ]---------")
  countAndSay(10)
  println("------------------------------")

  def countAndLog(n: Int): Writer[Vector[String], Int] = {
    @tailrec
    def inner(writer: Writer[Vector[String], Int]): Writer[Vector[String], Int] =
      if (writer.value >= n) writer.mapWritten(logs => logs.dropRight(1) :+ s"finished at ${writer.value}")
      else inner(writer.mapBoth { (log, value) =>
        val nextValue = value + 1
        (log :+ s"still counting $nextValue") -> nextValue
      })

    inner(Writer(Vector("starting at 0"), 0))
  }


  println("---------[ OUTPUT 5 ]---------")
  countAndLog(10).written.foreach(println)
  println("------------------------------")

  // Exercise 2: track the values as we sum things
  def naiveSum(n: Int): Int = {
    if (n <= 0) return 0
    else {
      println(s"Now we're at: $n")
      val subTotal = naiveSum(n - 1)
      println(s"Computed: sum(${n - 1}) = $subTotal")
      subTotal + n
    }
  }

  println("---------[ OUTPUT 6 ]---------")
  println(naiveSum(10))
  println("------------------------------")

  def writerSum(n: Int): Writer[Vector[String], Int] = {
    @tailrec
    def inner(idx: Int, writer: Writer[Vector[String], Int]): Writer[Vector[String], Int] = {
      if (idx > 10) writer
      else inner(idx + 1, writer.mapBoth {(logs, value) =>
        val nextValue = value + idx
        (logs
          :+ s"Now we're at: $idx"
          :+ s"Computed sum($idx) = $nextValue") -> nextValue
      })
    }

    inner(0, Writer(Vector(), 0))
  }

  println("---------[ OUTPUT 7 ]---------")
  val sum = writerSum(10)
  sum.written.foreach(println)
  println(s"Final value: ${sum.value}")
  println("------------------------------")

  def writerSumStackRec(n: Int): Writer[Vector[String], Int] = {
    if (n <= 0) Writer(Vector(), 0)
    else {
      for {
        _ <- Writer(Vector(s"Now we're at $n"), -1 /* the value does not matter*/)
        subTotal <- writerSumStackRec(n - 1)
        _ <- Writer(Vector(s"Computed sum(${n - 1}) = $subTotal"), -1 /* the value does not matter*/)
      } yield {
        // change the value here, but the logs from all the writers above will be combined
        subTotal + n
      }
    }
  }

  println("---------[ OUTPUT 8 ]---------")
  val sum2 = writerSumStackRec(10)
  sum2.written.foreach(println)
  println(s"Final value: ${sum2.value}")
  println("------------------------------")
}
