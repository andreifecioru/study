# Install SSH keys for the Hadoop account
define hadoop::ssh_key(
  $home_dir,
  $owner,
  $group,
) {
  File {
    owner   => $owner,
    group   => $group,
  }

  $ssh_dir = "${home_dir}/.ssh"

  file { $ssh_dir:
    ensure  => directory,
  }
  file { "${ssh_dir}/id_rsa":
    source => 'puppet:///modules/hadoop/ssh/hadoop_rsa',
    mode   => '0400',
  }
  file { "${ssh_dir}/authorized_keys":
    source => 'puppet:///modules/hadoop/ssh/hadoop_rsa.pub',
    mode   => '0600'
  }
  file { "${ssh_dir}/config":
    source => 'puppet:///modules/hadoop/ssh/config',
  }
}
