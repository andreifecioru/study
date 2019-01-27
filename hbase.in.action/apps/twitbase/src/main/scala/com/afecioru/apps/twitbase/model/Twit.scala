package com.afecioru.apps.twitbase.model

import org.joda.time.DateTime

import org.apache.hadoop.hbase.util.Bytes

import com.afecioru.apps.twitbase.dao.Utils.md5Hash


case class Twit(
  userName: String,
  timestamp: Long,
  content: String
) {
  lazy val key: Array[Byte] = {
    val userNameHash = md5Hash(userName)
    // multiplying by -1 makes the records ordered descending
    // by timestamp (most recent first)
    val ts = Bytes.toBytes(-1 * timestamp)

    val key = new Array[Byte](userNameHash.length + ts.length)
    Array.copy(userNameHash, 0, key, 0, userNameHash.length)
    Array.copy(ts, 0, key, userNameHash.length, ts.length)

    key
  }

  override def toString: String = {
    s"Twit($userName), ${new DateTime(timestamp)}, $content)"
  }
}
