package com.afecioru

import com.afecioru.apps.dao.HBase
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Connection
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

package object apps {
  object Traits {
    trait SparkSetup {
      private val master = "local[*]"

      def appName: String

      private lazy val sparkConf: SparkConf = new SparkConf()
        .setMaster(master)
        .set("spark.hbase.host", "hbase-box")
        .setAppName(appName)

      lazy val ss: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
      lazy val sc: SparkContext = ss.sparkContext
      lazy val sqlCtx: SQLContext = ss.sqlContext
    }

    trait HBaseSetup {
      lazy val hbaseConf: Configuration = HBaseConfiguration.create()
      lazy val connection: Connection = HBase.createConnection(hbaseConf)
    }

  }

}
