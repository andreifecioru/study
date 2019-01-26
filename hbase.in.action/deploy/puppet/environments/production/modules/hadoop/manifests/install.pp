# Install Hadoop software
class hadoop::install {
  include wget

  wget::fetch { 'hadoop-2.7.3.tar.gz':
    source      => 'https://archive.apache.org/dist/hadoop/core/hadoop-2.7.3/hadoop-2.7.3.tar.gz',
    destination => '/var/',
    timeout     => 0,
    verbose     => false,
  }

  exec { 'hadoop unpack and install':
    command => 'tar xzf hadoop-2.7.3.tar.gz && \
                mv hadoop-2.7.3 /usr/local/hadoop',
    cwd     => '/var',
    creates => '/usr/local/hadoop',
    require => Wget::Fetch['hadoop-2.7.3.tar.gz'],
    path    => ['/bin'],
  }
}
