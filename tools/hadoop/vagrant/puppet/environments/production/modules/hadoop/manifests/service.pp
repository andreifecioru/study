# Starts the Hadoop stack
class hadoop::service {
  Exec {
    path      => ['/bin', '/usr/bin',
                  '/usr/local/hadoop/bin', '/usr/local/hadoop/sbin'],
    # logoutput => true,
  }

  exec { 'hadoop_namenode_format':
    command => 'hdfs namenode -format -force',
  }

  exec { 'hadoop_start_dfs':
    command => 'start-dfs.sh',
    require => Exec['hadoop_namenode_format'],
  }

  exec { 'hadoop_start_yarn':
    command => 'start-yarn.sh',
    require => Exec['hadoop_namenode_format'],
  }
}
