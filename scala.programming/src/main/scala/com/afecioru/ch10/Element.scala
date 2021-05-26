package main.scala.com.afecioru.ch10


abstract class Element {
  def content: Array[String]
  def width: Int
  def height: Int

  private val PaddingChar = "."

  override def toString: String = content.mkString("\n")

  def above(other: Element): Element = {
    require(width == other.width)
    Element((content ++ other.content).toIndexedSeq: _*)
  }
  def below(other: Element): Element = other above this

  def leftOf(other: Element): Element = {
    require(height == other.height)

    val lines = content.zip(other.content).map(pair => pair._1 + pair._2)
    Element(lines.toIndexedSeq: _*)
  }

  def rightOf(other: Element): Element = other leftOf this

  def widen(w: Int): Element = {
    if (w > width) {
      val padLeft = PaddingChar * ((w - width) / 2)
      val padRight = PaddingChar * (w - width - padLeft.length)
      val lines = content.map(line => s"$padLeft$line$padRight")
      Element(lines.toIndexedSeq: _*)
    }
    else this
  }

  def heighten(h: Int): Element = {
    if (h > height) {
      val linesAbove = Array.fill((h - height) / 2)(PaddingChar * width)
      val linesBelow = Array.fill(h - linesAbove.length - height)(PaddingChar * width)
      Element((linesAbove ++ content ++ linesBelow).toIndexedSeq: _*)
    }
    else this
  }
}


object Element {
  private class ArrayElement(override val content: Array[String]) extends Element {
    if (content.nonEmpty) {
      require(content.forall(_.length == width))
    }

    override def height: Int = content.length
    override def width: Int = if (height == 0) 0 else content(0).length
  }

  private class LineElement(line: String) extends ArrayElement(Array(line))

  private class UniformElement(
      char: Char,
      override val width: Int,
      override val height: Int) extends Element {

    require(width > 0)
    require(height > 0)

    override def content: Array[String] = Array.fill(height)(char.toString * width)
  }

  def apply(contents: String*): Element = {
    if (contents.length == 1)
      new LineElement(contents(0))
    else
      new ArrayElement(contents.toArray)
  }

  def apply(char: Char, width: Int, height: Int): Element =
    new UniformElement(char, width, height)
}

object Spiral {
  private def renderSpiral(numEdges: Int,
                           direction: Int = 0,
                           acc: Element = Element('+', 1, 1)): Element = {
    if (numEdges == 0) acc
    else direction match {
      case 0 => { // up

      }
    }
    numEdges match {
      case 0 => acc
      case _: Int => direction match {
        case
      }
    }

  }
  def apply(numEdges: Int): Element = renderSpiral(numEdges)
}
