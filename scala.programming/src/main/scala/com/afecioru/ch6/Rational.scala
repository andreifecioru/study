package main.scala.com.afecioru.ch6

import scala.annotation.tailrec

class Rational private (nom: Int, denom: Int) {
  require(denom != 0)

  private val _nom: Int = if (nom * denom > 0) nom.abs else -nom.abs
  private val _denom: Int = math.abs(denom)

  override def toString: String = if (_denom == 1) _nom.toString else s"${_nom} / ${_denom}"

  def +(other: Rational): Rational =
    Rational(_nom * other._denom + other._nom * _denom,
      _denom * other._denom)

  def +(other: Int): Rational = this + Rational(other)

  def unary_- : Rational = Rational(-nom, denom)

  def -(other: Rational): Rational = this + (-other)
  def -(other: Int): Rational = this - Rational(other)

  def *(other: Rational): Rational =
    Rational(_nom * other._nom, _denom * other._denom)
  def *(other: Int): Rational = this * Rational(other)

  def /(other: Rational): Rational = this * Rational(other._denom, other._nom)
  def /(other: Int): Rational = this / Rational(other)

  def <(other: Rational): Boolean =
    _nom * other._denom < other._nom * _denom

  def >(other: Rational): Boolean = !(this < other)

  def max(other: Rational): Rational = if (this > other) this else other
  def min(other: Rational): Rational = if (this < other) this else other
}

object Rational {
  @tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def apply(nom: Int, denom:Int = 1): Rational = {
    val _gcd = gcd(nom max denom, nom min denom)
    new Rational(nom / _gcd, denom / _gcd)
  }

  //  implicit def int2Rational(num: Int): Rational = Rational(num)
  implicit class Int2Rational(num: Int) {
    def + (other: Rational): Rational = Rational(num) + other
  }
}
