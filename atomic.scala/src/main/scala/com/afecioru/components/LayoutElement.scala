package com.afecioru.components

abstract class LayoutElement {
  import LayoutElement._

  require(height == 0 || contents.forall(_.length == contents(0).length))

  def contents: Array[String]

  def height: Int = contents.length
  def width: Int = if (height == 0) 0 else contents(0).length

  def above(other: LayoutElement): LayoutElement = {
    val _this = widen(other.width)
    val _other = other.widen(width)

    element(_this.contents ++ _other.contents)
  }

  def below(other: LayoutElement): LayoutElement = {
    val _this = widen(other.width)
    val _other = other.widen(width)
    element(_other.contents ++ _this.contents)
  }

  private def combineLines(other: LayoutElement, op: (String, String) => String): LayoutElement = {
    // val result = contents.zip(other.contents).map { case (elem, otherElem) => elem + otherElem }
    val result = for {
      (line, lineOther) <- contents zip other.contents
    } yield op(line, lineOther)
    element(result)
  }

  def before(other: LayoutElement): LayoutElement = {
    val _this = heighten(other.height)
    val _other = other.heighten(height)

    _this.combineLines(_other, _ + _)
  }

  def after(other: LayoutElement): LayoutElement = {
    val _this = heighten(other.height)
    val _other = other.heighten(height)

    _other.combineLines(_this, _ + _)
  }

  def widen(w: Int): LayoutElement = {
    if (w <= width) this
    else {
      val left = element(EMPTY_SPACE, (w - width) / 2, height)
      val right = element(EMPTY_SPACE, w - left.width - width, height)
      left before this before right
    }
  }

  def heighten(h: Int): LayoutElement = {
    if (h <= height) this
    else {
      val top = element(EMPTY_SPACE, width, (h - height) / 2)
      val bottom = element(EMPTY_SPACE, width, h - height - top.height)
      top above this above bottom
    }
  }

  override def toString: String = contents.mkString("\n")
}


object LayoutElement {
  val EMPTY_SPACE: Char = ' '

  // the parametric field (i.e. `contents`)
  // overrides the abstract method in the base class.
  private class ArrayLayoutElement(override val contents: Array[String]) extends LayoutElement


  private class LineElement(s: String) extends ArrayLayoutElement(Array(s)) {
    override def height: Int = 1
    override def width: Int = s.length
  }

  private class UniformLayoutElement(
    chr: Char,
    override val width: Int,
    override val height: Int
  ) extends ArrayLayoutElement(Array.fill(height)(chr.toString * width))


  def element(contents: Array[String]): LayoutElement = new ArrayLayoutElement(contents)
  def element(s: String): LayoutElement = new LineElement(s)
  def element(chr: Char, width: Int, height: Int): LayoutElement = new UniformLayoutElement(chr, width, height)
}
