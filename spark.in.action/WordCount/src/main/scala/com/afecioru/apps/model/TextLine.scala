package com.afecioru.apps.model

import com.afecioru.apps.dao.Utils.md5Hash
import org.apache.hadoop.hbase.util.Bytes

case class TextLine(text: String) {
  lazy val key = md5Hash(text)

  def words: List[String] = {
    text.split("\\s+").toList
  }

  def bytes: Array[Byte] = {
    Bytes.toBytes(text)
  }

}

object TextLine {
  def apply(bytes: Array[Byte]): TextLine = {
    TextLine(Bytes.toString(bytes))
  }
}
