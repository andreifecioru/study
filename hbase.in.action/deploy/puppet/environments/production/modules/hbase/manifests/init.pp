# Deploys the HBase software
class hbase {
  contain ::hbase::install
  contain ::hbase::config
  contain ::hbase::service

  Class['::hbase::install']
  -> Class['::hbase::config']
  -> Class['::hbase::service']
}
