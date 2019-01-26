package com.afecioru.apps.twitbase

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Result}
import org.apache.hadoop.hbase.util.Bytes

import com.afecioru.apps.twitbase.model.User


package object dao {
  object Constants {
    val TABLE_NAME_STRING = "users"
    val TABLE_NAME: Array[Byte] = Bytes.toBytes(TABLE_NAME_STRING)

    val COL_FAM_INFO: Array[Byte] = Bytes.toBytes("info")

    val COL_USER_NAME: Array[Byte] = Bytes.toBytes("user_name")
    val COL_NAME: Array[Byte] = Bytes.toBytes("name")
    val COL_EMAIL: Array[Byte] = Bytes.toBytes("email")
    val COL_PASS: Array[Byte] = Bytes.toBytes("pass")
    val COL_TWEETS: Array[Byte] = Bytes.toBytes("tweets")
  }

  object HBase {
    private val DEFAULT_CONFIG = HBaseConfiguration.create()

    def createConnection(config: Configuration = DEFAULT_CONFIG): Connection = {
      ConnectionFactory.createConnection(config)
    }
  }

  object Converters {
    import Constants._

    implicit def result2User(result: Result): User = {
      val userNameBytes = result.getValue(COL_FAM_INFO, COL_USER_NAME);
      val nameBytes = result.getValue(COL_FAM_INFO, COL_NAME)
      val emailBytes = result.getValue(COL_FAM_INFO, COL_EMAIL)
      val passwordBytes = result.getValue(COL_FAM_INFO, COL_PASS)
      val tweetsBytes = result.getValue(COL_FAM_INFO, COL_TWEETS)

      val user = User(
        userName = Bytes.toString(userNameBytes),
        name = Bytes.toString(nameBytes),
        email = Bytes.toString(emailBytes),
        password = Bytes.toString(passwordBytes),
      )

      if (tweetsBytes != null)
        user.copy(tweets = Bytes.toLong(tweetsBytes))
      else
        user
    }
  }
}
