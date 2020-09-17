#!/bin/bash

wait_for_port() {
    local host=$1
    local port=$2
    local wait_for=$3

    timeout $wait_for bash -c 'until printf "" 2>>/dev/null >>/dev/tcp/$0/$1; do sleep 1; done' $host $port
}

wait_for_http_endpoint() {
    local host=$1
    local port=$2
    local path=$3

    echo "Waiting for HTTP endpoint $host:$port/$path to become available..."
    until [ "$(curl -o /dev/null -Isw '%{http_code}\n' http://$host:$port/$path)" == "200" ]; do 
        echo "Waiting for HTTP endpoint $host:$port/$path to become available..."
        sleep 1; 
    done
}

start_sshd() {
    echo "Starting SSHD service"
    rc-status
    rc-service sshd start
    sleep 3
}
