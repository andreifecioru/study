package com.afecioru.apps.twitbase.dao

import com.afecioru.apps.twitbase.dao.Types.Schema
import com.afecioru.apps.twitbase.model.Twit
import org.apache.hadoop.hbase.client.{Connection, Put, Scan, Table}
import org.apache.hadoop.hbase.util.Bytes

import Utils.md5Hash
import Converters._

class TwitDao(private val connection: Connection) {
  import TwitDao._

  implicit val _connection: Connection = connection
  private lazy val table: Table = HBase.getTable(SCHEMA)

  def addTwit(twit: Twit): Unit = {
    println(s"Adding twit: $twit")
    table.put(mkPut(twit))
  }

  def listTwitsForUser(userName: String): List[Twit] = {
    HBase.collectScanner(table.getScanner(mkScan(userName)))
  }


  // -------------[ HELPERS ]---------------
  private def mkScan(userName: String): Scan = {
    import Constants._

    val startRow = Bytes.padTail(md5Hash(userName), LONG_LENGTH)

    // increment the last byte of the user-name hash
    // to define the stop-key. This causes the scanner
    // to go through all the twits of the passed-in user name.
    val stopRow = Bytes.padTail(md5Hash(userName), LONG_LENGTH)
    val lastByte: Byte = stopRow(MD5_HASH_LENGTH - 1)
    stopRow(MD5_HASH_LENGTH - 1) = (lastByte + 1).asInstanceOf[Byte]


    new Scan().withStartRow(startRow).withStopRow(stopRow)
  }

  private def mkPut(twit: Twit): Put = {
    val put = new Put(twit.key)

    put.addColumn(COL_FAM_INFO, COL_USER_NAME, Bytes.toBytes(twit.userName))
    put.addColumn(COL_FAM_INFO, COL_CONTENT, Bytes.toBytes(twit.content))

    put
  }
}

object TwitDao {
  val TABLE_NAME = "twits"

  val COL_FAM_INFO: Array[Byte] = Bytes.toBytes("info")

  val COL_USER_NAME: Array[Byte] = Bytes.toBytes("un")
  val COL_CONTENT: Array[Byte] = Bytes.toBytes("cnt")

  val SCHEMA = Schema(TABLE_NAME, List(
    Bytes.toString(COL_FAM_INFO) ))
}
