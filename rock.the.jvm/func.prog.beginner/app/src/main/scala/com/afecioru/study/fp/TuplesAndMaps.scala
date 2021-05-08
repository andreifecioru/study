package com.afecioru.study.fp

object TuplesAndMaps extends App {


  // ------------------------------[ TUPLES ]------------------------

  /**
   * Tuples are ordered collections of heterogeneous things (i.e. different types). They are very similar with Python's
   * tuple data structure:
   *   - supported via the TupleX family of traits (Tuple1...Tuple22)
   */

  val aTuple = (2, "Andrei") // type is Tuples[Int, String] <=> [Int, String]
  println(aTuple)
  println(aTuple.swap)

  // elements are visible via _1 ... _22 fields

  // lots of the feature of case classes
  val anotherTuple = aTuple.copy(_2 = "Radu")
  println(anotherTuple)

  println("\n-----------------------------------------------\n\n")

  // ------------------------------[ MAPS ]------------------------

  /**
   * Maps are associations between keys and values. To define a map you need 2 type params:
   * one for key and one for value.
   */

  val aMap: Map[String, Int] = Map(
    ("Andrei", 10),
    "Radu"    -> 20,  // syntactic sugar for creating a Tuple2[T1, T2]
    "Dumitru" -> 30
  )

  // query by key (via the `apply()` implementation) - throws exception for non-existing keys
  println(aMap("Andrei"))

  // it's often more desirable to call `get()` witch lifts the result into an Option
  println(aMap.get("Andrei"))

  // containment checks
  println(aMap.contains("Radu"))

  // maps with default values (similar with defaultdict in Python)
  val mapWithDefault = Map(
    "Apple" -> "fruit",
    "Tomato" -> "fruit",
    "Potato" -> "vegetable"
  ).withDefaultValue("something else")
  println(mapWithDefault("Banana"))

  // we can produce computed value when we query for non-existing keys
  val mapWithDefault1 = mapWithDefault.withDefault(_.length)
  println(mapWithDefault1("George"))

  // add a new mapping (creates a new Map instance)
  println(aMap + ("George" -> 100))

  // map, flatMap, filter, etc
  val transformedMap = aMap.map {
    case (k, v) => k.toLowerCase -> (v + 100)
  }
  println(transformedMap)

  val transformedMap1 = aMap.flatMap {
    case (k, v) => Map(
      s"${k.toUpperCase}1" -> (v + 1),
      s"${k.toUpperCase}2" -> (v + 2),
      s"${k.toUpperCase}3" -> (v + 3),
    )
  }
  println(transformedMap1)

  // using map views does not eagerly create a new collection (items are produced on demand)
  val filteredMap = transformedMap1.view.filterKeys(_.endsWith("1"))
  // items in the view are generated only when the collection is materialized
  println(filteredMap.toMap)

  // in this case where we apply multiple transformers, using a view will avoid
  // creating intermediary collections effectively fusing transformers into a single one
  val transformedMap2 = transformedMap1.view
    .filter { case (_, v) => v > 30 }
    .mapValues(_ * 10)
    .toMap
  println(transformedMap2)

  // conversions to/from other collections
  println(aMap.toList)
  println(List("Andrei" -> 10, "Radu" -> 20).toMap)

  // grouping values
  val aList = List("Andrei", "Andreea", "Ion", "Iulian", "Dragos", "Dumitru")
  val namesGroupedByInitial = aList.groupBy(_.head) // this produces a Map
  println(namesGroupedByInitial)
}
