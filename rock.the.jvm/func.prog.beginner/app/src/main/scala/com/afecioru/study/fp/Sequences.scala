package com.afecioru.study.fp

import scala.util.Random

object Sequences extends App {
  /**
   * Sequences: data structures that are ordered and can be indexed
   * Operations:
   *  - iterator (i.e. cursors for traversing in order)
   *  - length/size, reversing, etc. In general anything that has to do with ordering
   *  - concatenation, appending, pre-pending, insertion at particular indices
   *  - grouping, sorting, zipping, searching, slicing
   *
   *  NOTE: be default, we are working with the immutable flavor of collections
   */

  // --------------------[ SEQUENCES ]-----------------------

  val aSequence = Seq(1, 2, 3, 4) // the `apply()` factory method actually instantiates the `List` sub-type
  println(aSequence)

  // reversing
  println(aSequence.reverse)

  // indexing: via the `apply()` on the instance object (don't use brackets like [])
  println(aSequence(2))

  // concatenation (produces a new list)
  println(aSequence ++ Seq(10, 11, 12))

  // sorting (descending)
  // NOTE: Seq[T] must hold a type T that can be ordered (i.e. there must be an Ordering[T] in the implicit scope)
  val desc: Ordering[Int] = (x, y) => y - x
  println(aSequence.sorted(desc))

  // Ranges (are also sequences)
  println((1 to 20 by 3).mkString(", "))

  println("\n-----------------------------------------------\n\n")

  // --------------------[ LISTS ]-----------------------

  /** Lists
   *  Core API:
   *
   *  sealed trait List[+T] // covariant
   *
   *  case object Nil extends List[Nothing] // Empty list
   *  case class ::[T](val head: T, val tail: List[T]) extends List[T] // the Cons
   *
   *  NOTES:
   *    - list extend LinerSeq: good for traversal and prepending
   *    - not so good for random access via index.
   *    - computing length is also O(n) - not so great
   */

  val aList = List(1, 2, 3)

  // prepend a value (the operators are associative to the side where the ":" is)
  println(42 :: aList)
  println(42 +: aList)

  // append a value
  println(aList :+ 42)

  // filling lists with a value
  println(List.fill(5)("Andrei"))

  // transform to string (similar to ", ".join(some_list) in Python
  println(aList.mkString(", "))

  println("\n-----------------------------------------------\n\n")

  // --------------------[ ARRAYS ]-----------------------

  /**
   * Are equivalent to Java arrays: mutable, good at random access via indexing.
   */
  val array = Array(1, 2, 3, 4)
  println(array)

  // construct arrays of pre-defined size w/o providing values
  val threeElems = Array.ofDim[Int](3)
  // values are initialized with default values based on the type of the data in the array
  println(threeElems.mkString(", "))

  // arrays are mutable
  threeElems(1) = 10  // syntactic sugar for threeElems.update(1, 10)
  println(threeElems.mkString(", "))

  // arrays are implicitly translated to sequences (i.e. WrappedArray instances)
  val arraySeq: Seq[Int] = threeElems
  println(arraySeq)

  // --------------------[ VECTOR ]-----------------------

  /***
   * A hybrid immutable data structures optimized for general purpose work:
   *   - effectively constant random access via indices
   *   - fast append/prepend
   *   - good perf. for large data sets
   */
  val vector = Vector(1, 2, 3)
  println(vector)

  // vectors vs. lists perf test
  val MAX_RUNS = 1000
  val MAX_CAPACITY = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val rand = new Random()

    val durations = for {
      it <- 1 to MAX_RUNS
    } yield {
      val currentTime = System.nanoTime()

      // operation
      collection.updated(rand.nextInt(MAX_CAPACITY), 0)

      System.nanoTime() - currentTime
    }

    durations.sum.toDouble / MAX_RUNS
  }

  val data = Array.ofDim[Int](MAX_CAPACITY)
  val writeTimeVector = getWriteTime(data.toVector)
  println(s"Avg write time for vectors: $writeTimeVector")

  val writeTimeLists = getWriteTime(data.toList)
  println(s"Avg write time for lists: $writeTimeLists")
}
