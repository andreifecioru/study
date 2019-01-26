# Starts HBase software
class hbase::service {
  Exec {
    path      => ['/bin', '/usr/bin',
                  '/usr/local/hbase/bin',
                  '/usr/local/hadoop/bin', '/usr/local/hadoop/sbin'],
    # logoutput => true,
  }

  exec { 'hbase_start':
    command => 'start-hbase.sh',
  }
}
