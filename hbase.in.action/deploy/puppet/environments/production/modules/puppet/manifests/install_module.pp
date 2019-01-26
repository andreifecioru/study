# Install a puppet module
define puppet::install_module($module_version = '') {
  $command = $module_version ? {
    ''      => "puppet module install ${name}",
    default => "puppet module install ${name} --version ${module_version}"
  }

  exec { "install_${name}":
    command => $command,
    unless  => "puppet module list | grep ${name}",
    path    => ['/bin', '/usr/bin'],
  }
}
