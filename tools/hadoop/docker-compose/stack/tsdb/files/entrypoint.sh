#!/bin/bash

echo "Starting opentsdb.  It should be available on port 4242 momentarily."
/opt/bin/start_opentsdb.sh &

while [ 1 ]; do
    sleep 1
done
