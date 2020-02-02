package ch02

sealed class Expr

class Sum(val left: Expr, val right: Expr) : Expr()
class Subst(val left: Expr, val right: Expr) : Expr()
class Mul(val left: Expr, val right: Expr): Expr()
class Num(val value: Int) : Expr()

fun Expr.eval(): Int = when(this) {
    is Num -> value
    is Sum -> left.eval() + right.eval()
    is Subst -> left.eval() - right.eval()
    is Mul -> left.eval() * right.eval()
}

