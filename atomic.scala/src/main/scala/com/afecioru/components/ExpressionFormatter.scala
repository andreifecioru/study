package com.afecioru.components


sealed abstract class Expression

case class Var(name: String) extends Expression
case class Number(num: Double) extends Expression
case class UnOp(operator: String, e: Expression) extends Expression
case class BiOp(operator: String, left: Expression, right: Expression) extends Expression

class ExpressionFormatter {
  private val opGroups = Array(
    Set("|", "||"), // lowest precedence
    Set("&", "&&"),
    Set("^"),
    Set("==", "!="),
    Set("<", "<=", ">", ">="),
    Set("+", "-"),
    Set("*", "%"), // highest precedence
  )

  private val precedence = {
    val associations = for {
      grpIdx <- opGroups.indices
      op <- opGroups(grpIdx)
    } yield op -> grpIdx

    associations.toMap
  }

  // unary operators have the highest precedence of all
  private val unaryPrecedence = opGroups.length

  // fractions impose vertical layout (so we set a special precedence for it)
  private val fractionPrecedence = -1

  private def format(e: Expression, enclOpPrecedence: Int): LayoutElement = {
    import LayoutElement._
    import ExpressionFormatter._

    e match {
      case Var(name) => element(name)

      case Number(num) => element(stripDot(num.toString))

      case UnOp(op, arg) => element(op) before format(arg, unaryPrecedence)

      // factions use vertical layout
      case BiOp("/", left, right) =>
        val top = format(left, fractionPrecedence)
        val bottom = format(right, fractionPrecedence)
        val line = element('-', top.width max bottom.width, 1)
        val fraction = top above line above bottom

        if (enclOpPrecedence != fractionPrecedence) fraction
        // support for stacked fractions
        else element(" ") before fraction before element(" ")

      case BiOp(op, left, right) =>
        val opPrecedence = precedence(op)
        val _left = format(left, opPrecedence)

        // the op precedence fot the right operand in higher than for the left operand
        // this maintains proper associativity:
        //   BinOp("-", Var("a"), BinOp("-", Var("b"), Var("c")))
        // is translated to
        //   a - (b - c)
        val _right = format(right, opPrecedence + 1)

        val result = _left before element(s" $op ") before _right

        if (enclOpPrecedence <= opPrecedence) result
        else OPEN_PAREN before result before CLOSED_PAREN
    }

  }

  def format(e: Expression): LayoutElement = format(e, 0)
}

object ExpressionFormatter {
  import LayoutElement._

  private def stripDot(s: String): String =
    if (s endsWith ".0") s.substring(0, s.length - 2) else s

  private val OPEN_PAREN = element("(")
  private val CLOSED_PAREN = element(")")

}
