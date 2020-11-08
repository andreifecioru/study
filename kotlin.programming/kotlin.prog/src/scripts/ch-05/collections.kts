// ------------------[ ARRAYS ]---------------

// when dealing with primitive values use the specialized classes (no boxing)
val nums = intArrayOf(*(1 .. 10).toList().toIntArray()) // type is IntArray (not Array<Integer>)
println("Average value: ${nums.average()}")
println("Sum is: ${nums.sum()}")

// create an array based on a formula;
// the 2nd param is a function which receives the current index
// and produces the value to be placed at that index
val nums1 = Array(10) { idx -> idx % 3 }
nums1.forEach { print("$it ") }

// ----------------[ LISTS ]-----------------

// lists come in 2 flavors: immutable (default) and mutable
val list1 = listOf(*nums.toTypedArray())
val mutableList = mutableListOf(*nums.toTypedArray())

// test containment
println(5 in list1)
println(list1.contains(5))

val names = listOf("Andrei", "George", "Dumitru")
// since we are dealing with immutable lists, these ops are actually creating new lists
println(names + "Ion")
println(names - "Andrei")

val names2 = mutableListOf("Andrei", "Ion")
names2.add("Gigi")
names2.remove("Andrei")
println(names2)

// --------------------[ SETS ]------------------
// NOTE: both mutable and immutable variants are available
val set1 = setOf("Andrei", "Radu", "Ion")
val set2 = mutableSetOf("Andrei", "Radu", "Ion")

// --------------------[ MAPS ]-----------------
// NOTE: both mutable and immutable variants are available

// create a map from a list of Pair instances
// NOTE: trailing commas are ok
val map0 = mapOf(
    Pair("a", 1),
    Pair("b", 2),
    Pair("c", 3),
)
println(map0)

// same as above: to() extension func. is available
// on all objects and produces Pair instanctes
val map1 = mapOf(
    "a" to 1,
    "b" to 2,
    "c" to 3,
)
println(map1)

// containment checks
// checking if key exists can be done in 3 ways
println(map1.containsKey("a"))
println("a" in map1)
println(map1.contains("a"))

// can also check if value exists
println(map1.containsValue(3))

// getting a value from map (must use nullable type)
val value: Int? = map1["a"] // same as map1.get("a")
println(value)

// getting a value with default (if not present) - no longer nullable
val value1: Int = map1.getOrDefault("d", 4)
println(value1)

// external iteration
for (entry in map1) {
    println("Key: ${entry.key} | Value: ${entry.value}")
}

// easier, using destructuring
for ((key, value) in map1) {
    println("Key: $key | Value: $value")
}
