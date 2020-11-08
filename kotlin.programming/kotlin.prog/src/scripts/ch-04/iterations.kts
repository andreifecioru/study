// cycle through the indices of a collection
val names = listOf("Andrei", "Bogdan", "Radu", "Dumitru")
for (idx in names.indices) {
    println("Name: ${names[idx]}")
}

// cycle through values and indices at the same time
for ((idx, value) in names.withIndex()) {
    println("Value: $value at index: $idx")
}
