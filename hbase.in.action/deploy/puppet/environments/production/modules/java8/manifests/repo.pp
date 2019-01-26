# Install custom repo for Oracle's Java8 package
class java8::repo {
  include apt

  exec { 'oracle license':
    command => '/bin/echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
                /bin/echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections',
    path    => ['/bin', '/usr/bin'],
  }

  apt::ppa { 'ppa:webupd8team/java':
    require => Exec['oracle license'],
  }
}
