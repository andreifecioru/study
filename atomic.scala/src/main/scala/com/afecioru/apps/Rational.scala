package com.afecioru.apps

import com.afecioru.utils.AtomicTest._

import scala.annotation.tailrec

class Rational(n: Int, d: Int) {
  // preconditions
  require(d != 0)

  private val gcd = compute_gcd(n.abs, d.abs)

  val numer = n / gcd
  val denom = d / gcd

  // auxiliary constructor
  def this(n: Int) = this(n, 1)

  def +(other: Rational): Rational = Rational(
    numer * other.denom + other.numer * denom,
    denom * other.denom
  )
  // technically there is no need for this overload
  // due to the implementation of the implicit conversion
  def +(other: Int): Rational = this + Rational(other)

  def *(other: Rational): Rational = Rational(
    numer * other.numer,
    denom * other.denom
  )
  def *(other: Int): Rational = this * Rational(other)

  def <(other: Rational): Boolean = numer * other.denom < other.numer * denom
  def >(other: Rational): Boolean = !(this < other)

  def max(other: Rational): Rational = if (this < other) other else this

  override def toString = s"$numer/$denom"

  override def equals(obj: Any): Boolean = {
    if (!obj.isInstanceOf[Rational]) return false

    val other = obj.asInstanceOf[Rational]
    numer == other.numer && denom == other.denom
  }

  @tailrec
  private def compute_gcd(x: Int, y: Int): Int = if (y == 0) x else compute_gcd(y, x % y)
}


object Rational {
  def apply(n: Int, d: Int) = new Rational(n, d)
  def apply(n: Int) = new Rational(n)

  implicit def intToRational(n: Int): Rational = Rational(n)
}


object RationalApp extends App {
  // bring implicits in scope
  import Rational._

  val r1 = Rational(1, 2)
  val r2 = Rational(2 ,3)

  (r1 + r2) is Rational(7, 6)
  (r1 * r2) is Rational(1, 3)
  (r1 * 3) is Rational(3, 2)
  (3 * r1) is Rational(3, 2)

  (r1 < r2) is true
  (r1 > r2) is false

  (r1 max r2) is Rational(2, 3)

  Rational(5) is Rational(5, 1)
}