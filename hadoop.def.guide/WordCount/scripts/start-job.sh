#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
PROJECT_DIR="$DIR/.."

pushd $DIR

echo "Removing any previous output folders"
hadoop fs -rm -r -skipTrash /user/root/word_count/output

echo "Starting the word-count job"
env HADOOP_OPTS="$HADDOP_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=7000" \
hadoop jar $PROJECT_DIR/build/libs/WordCount.jar \
  com.afecioru.apps.wordcount.WordCountJob \
  -D mapred.reduce.tasks=2 \
  -D reducer.minWordCount=2 \
  /user/root/word_count/input/input.txt \
  /user/root/word_count/output \

popd



