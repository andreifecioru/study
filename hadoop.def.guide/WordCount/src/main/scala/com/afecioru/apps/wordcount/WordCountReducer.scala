package com.afecioru.apps.wordcount

import java.lang.Iterable

import scala.collection.JavaConverters._
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Reducer
import Types._

import scala.util.Try

class WordCountReducer
  extends Reducer[Text, LongWritable, Text, LongWritable] {

  val MIN_WORD_COUNT_KEY = "reducer.minWordCount"

  override def reduce(key: Text, values: Iterable[LongWritable], context: ReducerContext): Unit = {
    val minWordCountConfig = context.getConfiguration.get(MIN_WORD_COUNT_KEY)

    val minWordCount = Try(minWordCountConfig.toInt)
        .toOption.getOrElse(0)

    println(s"Min word count config: $minWordCountConfig")
    println(s"Min word count: $minWordCount")

    val sum = values.asScala.map(_.get).toList.sum

    if (sum >= minWordCount) {
      context.write(key, new LongWritable(sum))
    }
  }
}
