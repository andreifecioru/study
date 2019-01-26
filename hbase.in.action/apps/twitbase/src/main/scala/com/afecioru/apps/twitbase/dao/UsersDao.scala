package com.afecioru.apps.twitbase.dao

import org.apache.hadoop.hbase.client._
import com.afecioru.apps.twitbase.model.User
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.util.Bytes

import Constants._
import Converters._


class UsersDao(private val connection: Connection) {
  private lazy val table: Table = connection
    .getTable(TableName.valueOf(TABLE_NAME_STRING))


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
    def reduce(scanner: ResultScanner,
               users: List[User]): List[User] = {
      val result = scanner.next()

      if (result == null) users
      else {
        if (result.isEmpty)
          reduce(scanner, users)
        else
          reduce(scanner, result2User(result) :: users)
      }
    }

    val scanner = table.getScanner(mkScan())
    reduce(scanner, Nil).reverse
  }

  def deleteUser(userName: String): Unit = {
    table.delete(mkDelete(userName))
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
    val put = new Put(Bytes.toBytes(user.userName))

    put.addColumn(COL_FAM_INFO, COL_USER_NAME, Bytes.toBytes(user.userName));
    put.addColumn(COL_FAM_INFO, COL_NAME, Bytes.toBytes(user.name))
    put.addColumn(COL_FAM_INFO, COL_EMAIL, Bytes.toBytes(user.email))
    put.addColumn(COL_FAM_INFO, COL_PASS, Bytes.toBytes(user.password))

    put
  }

  private def mkDelete(userName: String): Delete = {
    new Delete(Bytes.toBytes(userName))
  }

}
