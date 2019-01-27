package com.afecioru.apps.twitbase.model

import org.apache.hadoop.hbase.util.Bytes

case class User(
  userName: String,
  name: String,
  email: String,
  password: String,
  tweets: Long = 0L
) {
  val key: Array[Byte] = Bytes.toBytes(userName)
}
