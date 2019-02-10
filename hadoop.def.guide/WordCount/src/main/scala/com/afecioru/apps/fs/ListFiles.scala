package com.afecioru.apps.fs


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}


object ListFiles extends App {
  val config = new Configuration()
  println(config.get("fs.defaultFS"))

  val hdfs = FileSystem.get(config)
  val fileStatus = hdfs.listStatus(new Path("/"))

  FileUtil.stat2Paths(fileStatus).foreach(println)
}
