import pulumi
from pulumi_aws import ec2

from vpc import configure_vpc
from backend import create_backend
from compute import create_private_ec2_instance


if __name__ == '__main__':
  vpc_data = configure_vpc()
  #backend_data = create_backend(vpc_data)
  private_ec2_instance = create_private_ec2_instance(vpc_data)