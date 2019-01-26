# Install Oracle's Java8 package
class java8::package {
  include java8::repo

  package { 'oracle-java8-installer':
    ensure  => installed,
    require => [ Class['java8::repo'], Exec['apt_update'] ],
  }
}
