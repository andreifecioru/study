#!/usr/bin/env bash
set -e

RESET_ALL="NO"
LOG_FILE="./start_services.log"
ERR_FILE="./start_services.err"

parse_cmd_args() {
  for i in "$@"; do
    case $i in
      --reset)
      RESET_ALL="YES"
      ;;

      *)
      ;;
    esac
  done
}

start_log() {
  echo -e "\n----------------------------\n" | tee -a ${LOG_FILE} >> ${ERR_FILE}
  echo -e "$(date)\n" | tee -a ${LOG_FILE} >> ${ERR_FILE}
  echo -e "----------------------------\n" | tee -a ${LOG_FILE} >> ${ERR_FILE}
}

reset_all() {
  echo "Resetting the entire Hadoop stack..."

  echo "  > Killing all Java processes..."
  pkill -9 java

  echo "  > Deleting HDFS storage area..."
  rm -rf /home/hadoop/hadoopinfra/hdfs/datanode/*
  rm -rf /home/hadoop/hadoopinfra/hdfs/namenode/*

  echo "  > Formatting namenode..."
  hadoop namenode -format >> ${LOG_FILE} 2>>${ERR_FILE}
}

start_services() {
  echo "Starting Hadoop services..."

  echo "  > Start HDFS"
  start-dfs.sh >> ${LOG_FILE} 2>>${ERR_FILE}

  echo "  > Start YARN"
  start-yarn.sh >> ${LOG_FILE} 2>>${ERR_FILE}

  echo "  > Start HBASE"
  start-hbase.sh >> ${LOG_FILE} 2>>${ERR_FILE}
}

main() {
  parse_cmd_args $@

  start_log

  [[ "${RESET_ALL}" == "YES" ]] && reset_all

  start_services

  echo "Done."
}

main $@
