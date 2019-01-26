package com.afecioru.samples

import java.util

import scala.collection.JavaConverters._
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{Cell, CellUtil, HBaseConfiguration, TableName}

import scala.util.Try


object SamplesApp extends App {
  val TABLE_NAME = Bytes.toBytes("users")
  val COL_FAM_INFO = Bytes.toBytes("info")
  val COL_AGE = Bytes.toBytes("age")

  // setup a connection
  println("Setting up the table connection.")
  val conf = HBaseConfiguration.create()
  val conn = ConnectionFactory.createConnection(conf)
  implicit val table:Table = conn.getTable(TableName.valueOf("users"))

//  sample1()
  sample2()

  // close the connection
  conn.close()


  // ---------------[ SAMPLES ]------------------
  def sample1()(implicit table: Table): Unit = {
    // put something in HBase
    save("andrei", 37)

    // ... and get it back
    println("\n")
    load("andrei").foreach(age => println(s"Age is: $age"))

    // delete the row
    println("\n")
    delete("andrei")

    println("\nGetting data after delete.")
    load("andrei") match {
      case Nil => println("No data found")
      case values => values.foreach(age => s"Age is $age")
    }
  }

  def sample2()(implicit table: Table): Unit = {
    // put something in HBase
    save("andrei", 20)
    save("andrei", 37)
    save("andrei", 40)

    // ... and get it back
    println("\n")
    load("andrei") match {
      case Nil => println("No data found")
      case values => values.foreach(age => println(s"Age is $age"))
    }
  }


  // ---------------[ HELPERS ]-------------------
  def save(name: String, age: Int)
              (implicit table: Table): Unit = {
    println(s"Putting data: $name => $age")
    val put = new Put(Bytes.toBytes(name))
    put.addColumn(COL_FAM_INFO, COL_AGE, Bytes.toBytes(age))
    table.put(put)
  }

  def load(name: String)(implicit table: Table): List[Int] = {
    println(s"Getting data: $name")

    val get = new Get(Bytes.toBytes(name))
    get.addFamily(COL_FAM_INFO)

    val result = table.get(get)
    if(result.isEmpty)
      List.empty
    else
      result.getColumnCells(COL_FAM_INFO, COL_AGE).asScala.toList
      .map(CellUtil.cloneValue).map(Bytes.toInt)
  }

  def delete(name: String)(implicit table: Table): Unit = {
    println(s"Deleting: $name")

    val delete = new Delete(Bytes.toBytes("andrei"))
    delete.addFamily(COL_FAM_INFO)
    table.delete(delete)
  }
}
