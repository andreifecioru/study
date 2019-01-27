package com.afecioru.apps.wordcount

import java.lang.Iterable
import scala.collection.JavaConverters._

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Reducer


import Types._

class WordCountReducer
  extends Reducer[Text, LongWritable, Text, LongWritable] {

  override def reduce(key: Text, values: Iterable[LongWritable], context: ReducerContext): Unit = {
    val sum = values.asScala.map(_.get).toList.sum
    context.write(key, new LongWritable(sum))
  }
}
