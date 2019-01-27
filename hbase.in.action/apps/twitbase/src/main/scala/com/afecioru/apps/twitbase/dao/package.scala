package com.afecioru.apps.twitbase

import java.security.MessageDigest

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import com.afecioru.apps.twitbase.model.{Twit, User}


package object dao {
  object Types {
    case class Schema(name: String,
                      colFamilies: List[String]) {
      lazy val tableName: Array[Byte] = Bytes.toBytes(name)
    }
  }

  object Constants {
    val LONG_LENGTH = 8
    val MD5_HASH_LENGTH = 16
  }

  object Utils {
    def md5Hash(in: String): Array[Byte] = {
      MessageDigest.getInstance("MD5").digest(Bytes.toBytes(in))
    }
  }

  object HBase {
    import Types._

    private val DEFAULT_CONFIG = HBaseConfiguration.create()

    def createConnection(config: Configuration = DEFAULT_CONFIG): Connection = {
      ConnectionFactory.createConnection(config)
    }

    def getTable(schema: Schema)
                (implicit connection: Connection): Table = {

      def _getTable(schema: String): Table =
        connection
          .getTable(TableName.valueOf(schema))

      def _createSchema(schema: Schema): Unit = {
        val admin = connection.getAdmin
        if (!admin.tableExists(TableName.valueOf(schema.name))) {
          println(s"Creating schema: $schema")
          val table = new HTableDescriptor(TableName.valueOf(schema.tableName))
          schema.colFamilies.foreach { cf =>
            table.addFamily(new HColumnDescriptor(cf))
          }
          admin.createTable(table)
        }
      }

      _createSchema(schema)
      _getTable(schema.name)
    }

    def collectScanner[T](scanner: ResultScanner)
                         (implicit convert: Result => T): List[T] = {
      def reduce(scanner: ResultScanner, acc: List[T]): List[T] = {
        val result = scanner.next()

        if (result == null) acc
        else {
          if (result.isEmpty)
            reduce(scanner, acc)
          else
            reduce(scanner, convert(result) :: acc)
        }
      }

      reduce(scanner, Nil).reverse
    }
  }

  object Converters {

    implicit def result2User(result: Result): User = {
      import UsersDao._

      val userNameBytes = result.getValue(COL_FAM_INFO, COL_USER_NAME)
      val nameBytes = result.getValue(COL_FAM_INFO, COL_NAME)
      val emailBytes = result.getValue(COL_FAM_INFO, COL_EMAIL)
      val passwordBytes = result.getValue(COL_FAM_INFO, COL_PASS)
      val tweetsBytes = result.getValue(COL_FAM_INFO, COL_TWEETS)

      val user = User(
        userName = Bytes.toString(userNameBytes),
        name = Bytes.toString(nameBytes),
        email = Bytes.toString(emailBytes),
        password = Bytes.toString(passwordBytes)
      )

      if (tweetsBytes != null)
        user.copy(tweets = Bytes.toLong(tweetsBytes))
      else
        user
    }

    implicit def result2Twit(result: Result): Twit = {
      import TwitDao._
      import Constants._

      val userNameBytes = result.getValue(COL_FAM_INFO, COL_USER_NAME)
      val contentBytes = result.getValue(COL_FAM_INFO, COL_CONTENT)

      val timestampBytes = new Array[Byte](LONG_LENGTH)
      Array.copy(result.getRow, MD5_HASH_LENGTH, timestampBytes, 0, LONG_LENGTH)

      Twit(
        userName = Bytes.toString(userNameBytes),
        timestamp = -1 * Bytes.toLong(timestampBytes),
        content = Bytes.toString(contentBytes)
      )
    }
  }
}
