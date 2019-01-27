package com.afecioru.apps.wordcount

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat


object WordCountJob {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Usage: WordCount <input path> <output path>")
    } else {
      val job = Job.getInstance()
      job.setJarByClass(WordCountJob.getClass)
      job.setJobName("Word Count Job")

      FileInputFormat.addInputPath(job, new Path(args(0)))
      FileOutputFormat.setOutputPath(job, new Path(args(1)))

      job.setMapperClass(classOf[WordCountMapper])
      job.setReducerClass(classOf[WordCountReducer])

      job.setOutputKeyClass(classOf[Text])
      job.setOutputValueClass(classOf[LongWritable])

      val result = if (job.waitForCompletion(true)) 0 else 1
      System.exit(result)
    }
  }
}

