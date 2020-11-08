#!/usr/bin/env kotlinc-jvm


java.io.File(".")
    .walk()
    .forEach {
        println(it)
    }

println(Runtime.getRuntime().availableProcessors())


