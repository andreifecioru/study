# Creates the user and user group for Hadoop
class hadoop::user {
  group { 'hadoop':
    ensure => present,
  }

  user { 'hadoop':
    ensure     => present,
    comment    => 'Hadoop user',
    gid        => 'hadoop',
    home       => '/home/hadoop',
    managehome => true,
  }
  -> hadoop::ssh_key { 'hadoop_ssh_key':
    home_dir => '/home/hadoop',
    owner    => 'hadoop',
    group    => 'hadoop'
  }

  hadoop::ssh_key { 'root_ssh_key':
    home_dir => '/root',
    owner    => 'root',
    group    => 'root'
  }
}
