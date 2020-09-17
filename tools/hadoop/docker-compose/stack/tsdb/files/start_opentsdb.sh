#!/bin/bash
source /tools/utils.sh

export TSDB_VERSION="::TSDB_VERSION::"

echo "Waiting for HBase to get ready..."
wait_for_http_endpoint hbase 16010 "master-status"

echo "Waiting for the OpenTSDB tables to be created..."
sleep 30

echo "Starting OpenTSDB..."
env LOG_FILE=/var/log/opentsdb/opentsdb.log /usr/local/bin/tsdb tsd --port=4242 --staticroot=/usr/local/share/opentsdb/static --cachedir=/tmp --auto-metric
