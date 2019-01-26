package com.afecioru.samples

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}

object  BasicOpsApp extends App {
  val TABLE_NAME = Bytes.toBytes("users")
  val COL_FAM_INFO = Bytes.toBytes("info")
  val COL_AGE = Bytes.toBytes("age")

  val conf = HBaseConfiguration.create()
  val conn = ConnectionFactory.createConnection(conf)

  val table = conn.getTable(TableName.valueOf("users"))

  // put something in HBase
  val put = new Put(Bytes.toBytes("andrei"))
  put.addColumn(COL_FAM_INFO, COL_AGE, Bytes.toBytes(37))
  table.put(put)

  // ... and get it back
  val get = new Get(Bytes.toBytes("andrei"))
  get.addFamily(COL_FAM_INFO)
  val result = table.get(get)

  println(s"Age is: ${Bytes.toInt(result.getValue(COL_FAM_INFO, COL_AGE))}")

  // delete the row
  println("Deleting data.")
  val delete = new Delete(Bytes.toBytes("andrei"))
  delete.addFamily(COL_FAM_INFO)
  table.delete(delete)

  conn.close()
}
