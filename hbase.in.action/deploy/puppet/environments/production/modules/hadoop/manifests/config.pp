# Configures Hadoop
class hadoop::config {
  File {
    owner  => 'root',
    group  => 'root',
    mode   => '0644',
  }

  $hadoop_home = '/usr/local/hadoop'
  $hadoop_conf_dir = "${hadoop_home}/etc/hadoop"
  $environment = [
    'JAVA_HOME=/usr/lib/jvm/java-8-oracle',
    "HADOOP_HOME=${hadoop_home}",
    "HADOOP_MAPRED_HOME=${hadoop_home}",
    "HADOOP_COMMON_HOME=${hadoop_home}",
    "HADOOP_HDFS_HOME=${hadoop_home}",
    "YARN_HOME=${hadoop_home}",
    "HADOOP_COMMON_LIB_NATIVE_DIR=${hadoop_home}/lib/native",
    "HADOOP_INSTALL=${hadoop_home}",
  ]

  file { '/etc/profile.d/hadoop_env_vars.sh':
    source => 'puppet:///modules/hadoop/hadoop-env-vars.sh',
    mode   => '0755',
  }

  file { "${hadoop_conf_dir}/hadoop-env.sh":
    source => 'puppet:///modules/hadoop/hadoop-env.sh',
    mode   => 'ug+x',
  }

  file { "${hadoop_conf_dir}/core-site.xml":
    source => 'puppet:///modules/hadoop/core-site.xml',
  }

  file { "${hadoop_conf_dir}/hdfs-site.xml":
    source => 'puppet:///modules/hadoop/hdfs-site.xml',
  }

  file { "${hadoop_conf_dir}/yarn-site.xml":
    source => 'puppet:///modules/hadoop/yarn-site.xml',
  }

  file { "${hadoop_conf_dir}/mapred-site.xml":
    source => 'puppet:///modules/hadoop/mapred-site.xml',
  }

  file { ['/home/hadoop/hadoopinfra',
          '/home/hadoop/hadoopinfra/hdfs',
          '/home/hadoop/hadoopinfra/hdfs/namenode',
          '/home/hadoop/hadoopinfra/hdfs/datanode'] :
    ensure  => directory,
    owner   => 'hadoop',
    group   => 'hadoop',
    require => Class['hadoop::user']
  }
}
