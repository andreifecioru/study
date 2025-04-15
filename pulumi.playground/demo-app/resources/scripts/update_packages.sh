#!/bin/bash
# Update package list
dnf check-update -y

# Update all packages
dnf update -y

# Install additional useful packages
dnf install -y vim htop

# Configure automatic updates
dnf install -y dnf-automatic
systemctl enable --now dnf-automatic.timer