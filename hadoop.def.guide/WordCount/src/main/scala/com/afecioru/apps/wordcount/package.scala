package com.afecioru.apps

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.{Mapper, Reducer}

package object wordcount {
  object Types {
    type MapperContext = Mapper[LongWritable, Text, Text, LongWritable]#Context
    type ReducerContext = Reducer[Text, LongWritable, Text, LongWritable]#Context
  }

}
