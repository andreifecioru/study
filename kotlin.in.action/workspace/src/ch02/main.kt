package ch02

import java.util.Random;

fun max(a: Int, b: Int): Int {
    return if (a > b)  a else b
}

fun min(a: Int, b: Int) = if (a < b) a else b

class Person(val firstName: String, val lastName: String) {
    private val random = Random()

    // lazily computed proper.
    val fullName: String get() = "$firstName $lastName"

    val age: Int get() = random.nextInt(100)
}

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    YELLOW(0, 255, 255);

    fun rgb() = (r * 256 + g) * 256 + b;
}

fun colorName(color: Color) = when(color) {
    Color.RED -> "red"
    Color.BLUE -> "blue"
    Color.GREEN ->"green"
    Color.YELLOW -> "yellow"
    else -> "Unknown color"
}

fun main(args: Array<String>) {
    println("max(3, 5) = ${max(3, 5)}")
    println("min(3, 5) = ${min(3, 5)}")

    val p = Person("Andrei", "Fecioru")
    println("${p.fullName} is ${p.age} year old.")

    val color = Color.BLUE;
    println("rgb(${colorName(color)}) = ${color.rgb()}")

    println("1 - 2 + 3 = ${eval(Sum(Subst(Num(1), Num(2)), Num(3)))}")

    // ranges
    for (i in 1..10 step 3) {
        println(i)
    }
}