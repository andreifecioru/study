package com.afecioru.apps.model

import com.afecioru.apps.dao.Utils.md5Hash

case class TextLine(text: String) {
  lazy val key = md5Hash(text)

  def words: List[String] = {
    text.split("\\s+").toList
  }

}
