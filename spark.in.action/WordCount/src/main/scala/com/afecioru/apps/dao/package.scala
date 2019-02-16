package com.afecioru.apps

import java.security.MessageDigest

import com.afecioru.apps.model.TextLine
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes


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
    implicit def result2TextLine(result: Result): TextLine = {
      import TextLineDao._

      val textBytes = result.getValue(COL_FAMILY_DATA, COL_LINE)

      TextLine(textBytes)
    }
  }


}
