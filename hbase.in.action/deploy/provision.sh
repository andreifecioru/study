#!/usr/bin/env bash
set -e

IP_ADDR="127.0.0.1"
FORCE_EXECUTION="NO"
HOSTNAME=$(hostname --short)
FQDN=${HOSTNAME}.sandbox.com
PROVISION_STAMP_FILE=/var/vagrant_provision

parse_cmd_args() {
  for i in "$@"; do
    case $i in
      --ip=*)
      IP_ADDR="${i#*=}"
      shift
      ;;

      --force)
      FORCE_EXECUTION="YES"
      ;;

      *)
      ;;
    esac
  done
}

install_packages() {
  for package in "$@"; do
    echo "Package: ${package}"
    apt-get install -y ${package} > /dev/null 2>&1
  done
}

install_puppet_module() {
  local module_name=""
  local module_version=""

  for module in $"$@"; do
    IFS=':' read module_name module_version <<< ${module}
    if [ -z module_version ]; then
      echo "Puppet module: ${module_name}"
      puppet module list | grep ${module_name} || \
      puppet install module ${module_name} > /dev/null 2>&1
    else
      echo "Puppet module: ${module_name}@${module_version}"
      puppet module list | grep ${module_name} || \
      puppet module install ${module_name} --version ${module_version} > /dev/null 2>&1
    fi
  done
}

update_hosts_file() {
  cat << EOF > /etc/hosts
127.0.0.1     localhost
${IP_ADDR}    ${FQDN} ${HOSTNAME}

# The following lines are desirable for IPv6 capable hosts
::1 ip6-localhost ip6-loopback
fe00::0 ip6-localnet
ff00::0 ip6-mcastprefix
ff02::1 ip6-allnodes
ff02::2 ip6-allrouters
ff02::3 ip6-allhosts
EOF
}

setup_environment() {
  echo "LANG=en_US.UTF-8" >> /etc/environment
  echo "LANGUAGE=en_US.UTF-8" >> /etc/environment
  echo "LC_ALL=en_US.UTF-8" >> /etc/environment
  echo "LC_CTYPE=en_US.UTF-8" >> /etc/environment
}

main() {
  setup_environment

  echo "Updating package cache..."
  apt-get update > /dev/null 2>&1

  echo "Installing utility packages..."
  install_packages tree htop software-properties-common

  echo "Updating the hosts file..."
  update_hosts_file

  echo "Installing puppet modules"
  install_puppet_module \
    puppetlabs-apt:2.4.0 \
    maestrodev-wget:1.7.3

  touch ${PROVISION_STAMP_FILE}
}

parse_cmd_args $@

if [ "${FORCE_EXECUTION}" == "YES" ]; then
  echo "Forcing provisioner execution..."
  rm -rf ${PROVISION_STAMP_FILE}
fi

if [ -f ${PROVISION_STAMP_FILE} ]; then
  echo "Already provisioned. Skipping..."
  exit 0
fi

main
