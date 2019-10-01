package ch02

import java.lang.IllegalArgumentException

interface Expr

class Num(val value: Int) : Expr

class Sum(val left: Expr, val right: Expr) : Expr
class Subst(val left: Expr, val right: Expr) : Expr
class Mul(val left: Expr, val right: Expr): Expr

fun eval(e: Expr): Int = when(e) {
    is Num -> e.value
    is Sum -> eval(e.left) + eval(e.right)
    is Subst -> eval(e.left) - eval(e.right)
    is Mul -> eval(e.left) * eval(e.right)

    else -> throw IllegalArgumentException("Unsupported operation")
}

