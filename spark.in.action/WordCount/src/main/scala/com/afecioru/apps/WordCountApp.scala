package com.afecioru.apps

import com.afecioru.apps.Traits.SparkSetup
import com.afecioru.apps.model.TextLine


object WordCountApp extends App with SparkSetup {
  import sqlCtx.implicits._

  val appName = "WordCount"

  val text =
    """
      |Lorem Ipsum is simply dummy text of the printing and typesetting industry.
      |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
      |when an unknown printer took a galley of type and scrambled it to make a type
      |specimen book. It has survived not only five centuries, but also the leap into
      |electronic typesetting, remaining essentially unchanged. It was popularised in
      |the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
      |and more recently with desktop publishing software like Aldus PageMaker including
      |versions of Lorem Ipsum.
    """.stripMargin

  val sentences = text.split('.').toSeq.map(TextLine)

  val df = ss.createDataset(sentences)
      .flatMap(_.words)
      .map(word => word -> 1)
      .withColumnRenamed("_1", "word")
      .withColumnRenamed("_2", "count")
      .groupBy($"word")
      .sum("count")

  df.printSchema()

  df.collect().foreach(println)

  ss.stop()
}
