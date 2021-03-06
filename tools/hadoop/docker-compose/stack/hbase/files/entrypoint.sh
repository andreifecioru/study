#!/bin/bash
source /tools/utils.sh

echo "Waiting for Hadoop to get ready..."
wait_for_port hadoop 9000 30


if [ $? -eq 0 ]; then
    echo "Hadoop is now ready. Let's start HBase..."
    start-hbase.sh

    echo "Waiting for HBase to get ready..."
    wait_for_http_endpoint hbase 16010 "master-status"
    echo "HBase is now ready. Create OpenTSDB tables..."

    env COMPRESSION=NONE /create_tsdb_tables.sh

    while true; do sleep 1; done
else
    echo "Unable to start HBase. Hadoop dependency not satisfied."
fi
