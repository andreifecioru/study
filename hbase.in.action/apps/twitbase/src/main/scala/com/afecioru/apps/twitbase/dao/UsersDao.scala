package com.afecioru.apps.twitbase.dao

import org.apache.hadoop.hbase.client._
import com.afecioru.apps.twitbase.model.User
import org.apache.hadoop.hbase.util.Bytes

import Types.Schema
import Converters._


class UsersDao(private val connection: Connection) {
  import UsersDao._

  implicit val _connection:Connection = connection
  private lazy val table: Table = HBase.getTable(SCHEMA)

  def addUser(user: User): Unit = {
    println(s"Adding user: $user")
    table.put(mkPut(user))
  }

  def getUser(userName: String): Option[User] = {
    val result = table.get(mkGet(userName))

    if (result.isEmpty)
      None
    else
      Some(result)
  }

  def listUsers(): List[User] = {
    HBase.collectScanner(table.getScanner(mkScan()))
  }

  def deleteUser(userName: String): Unit = {
    table.delete(mkDelete(userName))
  }

  def incrementTweetsForUser(userName: String, amount: Long): Unit = {
    table.increment(mkIncrementTweets(userName, amount))
  }

  // -------------[ HELPERS ]------------------
  private def mkGet(userName: String): Get = {
    val get = new Get(Bytes.toBytes(userName))
    get.addFamily(COL_FAM_INFO)
    get
  }

  private def mkScan(): Scan = {
    val scan = new Scan()
    scan.addFamily(COL_FAM_INFO)
    scan
  }

  private def mkPut(user: User): Put = {
    val put = new Put(user.key)

    put.addColumn(COL_FAM_INFO, COL_USER_NAME, Bytes.toBytes(user.userName));
    put.addColumn(COL_FAM_INFO, COL_NAME, Bytes.toBytes(user.name))
    put.addColumn(COL_FAM_INFO, COL_EMAIL, Bytes.toBytes(user.email))
    put.addColumn(COL_FAM_INFO, COL_PASS, Bytes.toBytes(user.password))

    put
  }

  private def mkDelete(userName: String): Delete = {
    new Delete(Bytes.toBytes(userName))
  }

  private def mkIncrementTweets(userName: String, amount: Long): Increment = {
    val increment = new Increment(Bytes.toBytes(userName))

    increment.addColumn(COL_FAM_INFO, COL_TWEETS, amount)

    increment
  }
}


object UsersDao {
  val TABLE_NAME = "users"

  val COL_FAM_INFO: Array[Byte] = Bytes.toBytes("info")

  val COL_USER_NAME: Array[Byte] = Bytes.toBytes("un")
  val COL_NAME: Array[Byte] = Bytes.toBytes("name")
  val COL_EMAIL: Array[Byte] = Bytes.toBytes("email")
  val COL_PASS: Array[Byte] = Bytes.toBytes("pass")
  val COL_TWEETS: Array[Byte] = Bytes.toBytes("tweets")

  val SCHEMA = Schema(TABLE_NAME, List(
    Bytes.toString(COL_FAM_INFO) ))
}
