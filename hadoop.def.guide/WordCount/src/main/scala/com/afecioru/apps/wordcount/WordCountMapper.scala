package com.afecioru.apps.wordcount

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import Types._

class WordCountMapper
  extends Mapper[LongWritable, Text, Text, LongWritable]  {

  override def map(key: LongWritable, value: Text, context: MapperContext): Unit = {
    value.toString.split("\\s+")
      .foreach(word =>
        context.write(new Text(word), new LongWritable(1L)))
  }
}
