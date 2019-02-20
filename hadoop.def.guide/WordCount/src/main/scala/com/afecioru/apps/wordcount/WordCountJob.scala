package com.afecioru.apps.wordcount

import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.util.{Tool, ToolRunner}


object WordCountJob extends Configured with Tool {
  override def run(args: Array[String]): Int = {
    if (args.length != 2) return -1

    val job = Job.getInstance(getConf)
    job.setJarByClass(WordCountJob.getClass)
    job.setJobName("Word Count Job")

    // Set the reducer's parallelism
    // job.setNumReduceTasks(2)

    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    job.setMapperClass(classOf[WordCountMapper])
    // for the word-count problem, the combiner implementation
    // is the same as the reducer implementation
    job.setCombinerClass(classOf[WordCountReducer])
    job.setReducerClass(classOf[WordCountReducer])

    /*
     * NOTE: when the mapper output key/value types
     * coincide with the reducer's output key/value types
     * we're not required to specify them.
     */
    // job.setMapOutputKeyClass(classOf[Text])
    // job.setMapOutputValueClass(classOf[LongWritable])

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[LongWritable])

    // The 'true' flag instructs the job to display the progress at the
    // console output.
    if (job.waitForCompletion(true)) 0 else 1
  }


  def main(args: Array[String]): Unit = {
    val exitCode = ToolRunner.run(WordCountJob, args)
    System.exit(exitCode)
  }
}

