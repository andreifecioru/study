package com.afecioru.apps.dao

import com.afecioru.apps.dao.Types.Schema
import com.afecioru.apps.model.TextLine
import org.apache.hadoop.hbase.client.{Connection, Put, Scan}
import org.apache.hadoop.hbase.util.Bytes

import Converters._


class TextLineDao(private val connection: Connection) {
  import TextLineDao._

  private implicit val _connection: Connection = connection
  private lazy val table = HBase.getTable(SCHEMA)

  def addTextLine(line: TextLine): Unit = {
    table.put(mkPut(line))
  }

  def listLines(): List[TextLine] = {
    HBase.collectScanner(table.getScanner(mkScan()))
  }

  // -------------[ HELPERS ]---------------
  private def mkScan(): Scan = {
    new Scan()
  }

  private def mkPut(line: TextLine): Put = {
    val put = new Put(line.key)
    put.addColumn(COL_FAMILY_DATA, COL_LINE, line.bytes)
    put
  }
}

object TextLineDao {
  val TABLE_NAME = "text"

  val COL_FAMILY_DATA: Array[Byte] = Bytes.toBytes("data")
  val COL_LINE: Array[Byte] = Bytes.toBytes("line")

  val SCHEMA = Schema(TABLE_NAME, List(
    Bytes.toString(COL_FAMILY_DATA) ))
}
