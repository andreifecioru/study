// when as an expression
fun ageCategory(age: Int): String = when { // matching must be exhaustive
    age < 5 -> "toddler"
    age < 12 -> "child"
    age < 18 -> "teenager"
    age < 65 -> "adult"
    else -> "elderly"  // default clause
}

// NOTE: 'else' clause is not required if 'when' is used as a statement (i.e. it's not a r-value)

println(ageCategory(14))

// various types of matching
fun whatToDo(day: Any): String = when (day) {
    "Saturday", "Sunday" -> "Chill."
    in listOf("Monday", "Tuesday", "Wednesday", "Thursday") -> "Work hard."
    in 2 .. 4 -> "Work hard."
    "Friday" -> "Waiting for the weekend."
    String -> "What?" // matching based on type
    else -> "No clue."
}

println("On 'Friday' we do: ${whatToDo("Friday")}")
println("On 'blah' we do: ${whatToDo("blah")}")
println("On '10' we do: ${whatToDo(10)}")

// restricting the variable scope
when (val numCores = Runtime.getRuntime().availableProcessors()) {
    1 -> println("Single-core mode.")
    in 2 .. 16 -> println("Using multi-core. ($numCores cores)")
    else -> println("Main-frame mode. ($numCores cores)")
}