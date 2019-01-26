# Configures HBase
class hbase::config {
  File {
    owner  => 'root',
    group  => 'root',
    mode   => '0644',
  }

  $hbase_home = '/usr/local/hbase'
  $hbase_conf_dir = "${hbase_home}/conf"

  file { '/etc/profile.d/hbase_env_vars.sh':
    source => 'puppet:///modules/hbase/hbase-env-vars.sh',
    mode   => '0755',
  }

  file { "${hbase_conf_dir}/hbase-env.sh":
    source => 'puppet:///modules/hbase/hbase-env.sh',
    mode   => 'ug+x',
  }

  file { "${hbase_conf_dir}/hbase-site.xml":
    source => 'puppet:///modules/hbase/hbase-site.xml',
  }
}
