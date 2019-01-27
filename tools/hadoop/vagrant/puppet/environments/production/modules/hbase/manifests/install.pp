# Install HBase software
class hbase::install {
  include wget

  wget::fetch { 'hbase-1.4.9-bin.tar.gz':
    source      => 'http://apache.javapipe.com/hbase/stable/hbase-1.4.9-bin.tar.gz',
    destination => '/var/',
    timeout     => 0,
    verbose     => false,
  }

  exec { 'hbase unpack and install':
    command => 'tar xzf hbase-1.4.9-bin.tar.gz && \
                mv hbase-1.4.9 /usr/local/hbase',
    cwd     => '/var',
    creates => '/usr/local/hbase',
    require => Wget::Fetch['hbase-1.4.9-bin.tar.gz'],
    path    => ['/bin'],
  }

}
