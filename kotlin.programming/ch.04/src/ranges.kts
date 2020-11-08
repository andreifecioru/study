// ranges are inclusive (both edges of the interval are included)
val someInts = 1 .. 10
someInts.forEach { print("$it ") }
println()

val someChars = 'a' .. 'f'
someChars.forEach { print("$it ") }
println()

// use `until` to create exclusive ranges (the end value is not included)
val someInts1 = 1 until 10
someInts1.forEach { print("$it ") }
println()

// reverse ranges
val reverseInts = 10 downTo 1
reverseInts.forEach { print("$it ") }
println()

// ranges with strides
val someInts2 = 1 .. 100 step 10
someInts2.forEach { print("$it ") }
println()

// filter some values
someInts2
    .filter { it % 3 ==0 }
    .forEach { print("$it ") }
println()

