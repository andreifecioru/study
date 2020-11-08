fun nofluff() {
    println("Some stuff")

    throw RuntimeException("Something went wrong...")
}

println("Doing some work")

try {
    nofluff()
} catch (e: Exception) {
    println(e.stackTrace[0])
    println(e.stackTrace[1])
}