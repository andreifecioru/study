package main.scala.com.afecioru.ch4

import scala.collection.mutable.{Map => MMap}

class ChecksumAccumulator {
  private[this] var sum = 0

  def add(byte: Byte): Unit = sum += byte
  def checksum(): Int = ~(sum & 0xFF) + 1
}

object ChecksumAccumulator {
  private val cache = MMap.empty[String, Int]

  def checksum(s: String): Int = {
    if (cache.contains((s))) cache(s)
    else {
      println(s"Computing checksum for $s")
      val result = s.foldLeft(new ChecksumAccumulator) ((acc, letter) => {
        acc add letter.toByte
        acc
      }).checksum()
      cache(s) = result
      result
    }
  }
}
