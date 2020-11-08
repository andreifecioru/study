java.io.File(".")
    .walk()
    .forEach {
        println(it)
    }