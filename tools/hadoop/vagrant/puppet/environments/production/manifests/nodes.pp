node 'hbase-box' {
  Exec { 'clean_up':
    command => 'pkill -9 java; \
                rm -rf /home/hadoop/hadoopinfra/hdfs/datanode/* && \
                rm -rf /home/hadoop/hadoopinfra/hdfs/namenode/*',
    path    => ['/bin', '/usr/bin'],
  }
  -> class { '::java8': }
  -> class { '::hadoop': }
  -> class { '::hbase': }
}
