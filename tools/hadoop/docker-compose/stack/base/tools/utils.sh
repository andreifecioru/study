#!/bin/bash

wait_for_port() {
    local host=$1
    local port=$2
    local wait_for=$3

    timeout $wait_for bash -c 'until printf "" 2>>/dev/null >>/dev/tcp/$0/$1; do sleep 1; done' $host $port
}