# Deploys the Hadoop stack
class hadoop {
  contain ::hadoop::user
  contain ::hadoop::install
  contain ::hadoop::config
  contain ::hadoop::service

  Class['::hadoop::user']
  -> Class['::hadoop::install']
  -> Class['::hadoop::config']
  -> Class['::hadoop::service']
}
