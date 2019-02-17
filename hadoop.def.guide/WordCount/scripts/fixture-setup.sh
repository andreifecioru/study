#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

pushd $DIR

echo "Creating HDFS folders"
hadoop fs -mkdir -p /user/root/word_count/input

echo "Copying input data"
hadoop fs -put ../src/main/resources/input.txt /user/root/word_count/input

echo "Removing any previous output folders"
hadoop fs -rm -r -skipTrash /user/root/word_count/output

popd



