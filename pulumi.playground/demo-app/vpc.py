import pulumi
from pulumi_aws import ec2, rds, iam

project_name = pulumi.get_project()
aws_config = pulumi.Config("aws")
aws_region = aws_config.require("region")


def create_vpc():
  return ec2.Vpc(f'{project_name}-vpc',
      cidr_block='10.0.0.0/16',
      tags={
          'Name': f'{project_name}-vpc',
          'Project': project_name
      }
  )


def create_internet_gateway(vpc):
  return ec2.InternetGateway(f'{project_name}-igw',
      vpc_id=vpc.id,
      tags={
          'Name': f'{project_name}-igw',
          'Project': project_name
      }
  )
  
  
def create_public_route_table(vpc, internet_gateway):
  public_route_table = ec2.RouteTable(f'{project_name}-public-rt',
      vpc_id=vpc.id,
      tags={
          'Name': f'{project_name}-public-rt',
          'Project': project_name
      }
  )

  # Setup a route to the Internet Gateway
  public_route = ec2.Route(f'{project_name}-public-route',
      route_table_id=public_route_table.id,
      destination_cidr_block='0.0.0.0/0',
      gateway_id=internet_gateway.id
  )
  
  return public_route_table


def create_private_route_table(vpc, nat_gateway):
  # Create a route table for private subnets
  private_route_table = ec2.RouteTable(f'{project_name}-private-rt',
      vpc_id=vpc.id,
      tags={
        'Name': f'{project_name}-private-rt',
        'Project': project_name
    }
  )

  # Create a route to the NAT Gateway
  private_route = ec2.Route(f'{project_name}-private-route',
      route_table_id=private_route_table.id,
      destination_cidr_block='0.0.0.0/0',
      nat_gateway_id=nat_gateway.id
  )
  
  return private_route_table


def create_public_subnet(vpc, zone, route_table):
  cidr_block_base = {'a' : 1, 'b' : 2, 'c' : 3}
  cidr_block = f'10.0.{cidr_block_base[zone]}.0/24'
  
  public_subnet = ec2.Subnet(f'{project_name}-public-subnet-{zone}',
      vpc_id=vpc.id,
      cidr_block=cidr_block,
      availability_zone=f'{aws_region}{zone}',
      map_public_ip_on_launch=True,
      tags={
          'Name': f'{project_name}-public-subnet-{zone}',
          'Project': project_name
      }
  )
  
  ec2.RouteTableAssociation(f'{project_name}-public-rta-{zone}',
    subnet_id=public_subnet.id,
    route_table_id=route_table.id
  )
  
  return public_subnet


def create_private_subnet(vpc, zone, route_table):
  cidr_block_base = {'a' : 11, 'b' : 12, 'c' : 13}
  cidr_block = f'10.0.{cidr_block_base[zone]}.0/24'
  
  private_subnet = ec2.Subnet(f'{project_name}-private-subnet-{zone}',
      vpc_id=vpc.id,
      cidr_block=cidr_block,
      availability_zone=f'{aws_region}{zone}',
      map_public_ip_on_launch=False,
      tags={
          'Name': f'{project_name}-private-subnet-{zone}',
          'Project': project_name
      }
  )
  
  ec2.RouteTableAssociation(f'{project_name}-private-rta-{zone}',
    subnet_id=private_subnet.id,
    route_table_id=route_table.id
  )
  
  return private_subnet


def create_nat_gateway(vpc, public_subnet):
  # Create an Elastic IP for the NAT Gateway
  nat_eip = ec2.Eip(f'{project_name}-nat-eip',
      tags={
        'Name': f'{project_name}-nat-eip',
        'Project': project_name
    }
  )

  # Create the NAT Gateway
  return ec2.NatGateway(f'{project_name}-nat',
      allocation_id=nat_eip.id,
      subnet_id=public_subnet.id,
      tags={
          'Name': f'{project_name}-nat',
          'Project': project_name
      }
  )


def create_public_subnets(vpc, route_table):
  subnets = {}
  for zone in ['a', 'b', 'c']:
    subnets[zone] = create_public_subnet(vpc, zone, route_table)
  return subnets


def create_private_subnets(vpc, route_table):
  subnets = {}
  for zone in ['a', 'b', 'c']:
    subnets[zone] = create_private_subnet(vpc, zone, route_table)
  return subnets


def create_bastion_sg(vpc):
    # Create the security group
    bastion_sg = ec2.SecurityGroup(f'{project_name}-bastion-sg',
        description='Security group for bastion host',
        vpc_id=vpc.id,
        tags={
            'Name': f'{project_name}-bastion-sg',
            'Project': project_name
        }
    )

    # Allow inbound SSH traffic
    ec2.SecurityGroupRule(f'{project_name}-bastion-ssh-in',
        type='ingress',
        from_port=22,
        to_port=22,
        protocol='tcp',
        cidr_blocks=['0.0.0.0/0'],
        security_group_id=bastion_sg.id,
        description='Allow SSH access from anywhere'
    )

    # Allow all outbound traffic
    ec2.SecurityGroupRule(f'{project_name}-bastion-all-out',
        type='egress',
        from_port=0,
        to_port=0,
        protocol='-1',
        cidr_blocks=['0.0.0.0/0'],
        security_group_id=bastion_sg.id,
        description='Allow all outbound traffic'
    )

    return bastion_sg 


def create_instance_sg(vpc, bastion_sg):
    # Create the security group
    instance_sg = ec2.SecurityGroup(f'{project_name}-instance-sg',
        description='Security group for instances in private subnets',
        vpc_id=vpc.id,
        tags={
            'Name': f'{project_name}-instance-sg',
            'Project': project_name
        }
    )

    # Allow inbound traffic from the bastion host
    ec2.SecurityGroupRule(f'{project_name}-instance-ssh-in',
        type='ingress',
        from_port=22,
        to_port=22,
        protocol='tcp',
        security_group_id=instance_sg.id,
        source_security_group_id=bastion_sg.id,
        description='Allow SSH access from bastion host'
    )
    
    # Allow all outbound traffic
    ec2.SecurityGroupRule(f'{project_name}-instance-all-out',
        type='egress',
        from_port=0,
        to_port=0,
        protocol='-1',
        cidr_blocks=['0.0.0.0/0'],
        security_group_id=instance_sg.id,
        description='Allow all outbound traffic'
    )

    return instance_sg
  

def create_rds_sg(vpc, bastion_sg):
    # Create the security group
    rds_sg = ec2.SecurityGroup(f'{project_name}-rds-sg',
        description='Security group for RDS instance',
        vpc_id=vpc.id,
        tags={
            'Name': f'{project_name}-rds-sg',
            'Project': project_name
        }
    )

    # Allow inbound traffic from the bastion host
    ec2.SecurityGroupRule(f'{project_name}-rds-postgres-in',
        type='ingress',
        from_port=5432,
        to_port=5432,
        protocol='tcp',
        security_group_id=rds_sg.id,
        source_security_group_id=bastion_sg.id,
        description='Allow Postgres access from bastion host'
    )

    # Allow all outbound traffic
    ec2.SecurityGroupRule(f'{project_name}-rds-all-out',
        type='egress',
        from_port=0,
        to_port=0,
        protocol='-1',
        cidr_blocks=['0.0.0.0/0'],
        security_group_id=rds_sg.id,
        description='Allow all outbound traffic'
    )

    return rds_sg  
  
  
def create_instance_key(vpc):
  # User data script to update the system
  with open('./resources/ssh-key.pub', 'r') as f:
    public_key = f.read() 
    
  # Create key pair for SSH access to instances
  return ec2.KeyPair(f'{project_name}-key',
      public_key=public_key,
      tags={
          'Name': f'{project_name}-key',
          'Project': project_name
      }
  )


def create_bastion_host(subnet, bastion_sg, instance_key):
  # User data script to update the system
  with open('./resources/scripts/update_packages.sh', 'r') as f:
    user_data = f.read() 
    
  return ec2.Instance('bastion-host',
      instance_type='t2.micro',
      ami='ami-0669b163befffbdfc',  # Amazon Linux 2023 AMI in eu-central-1
      subnet_id=subnet.id,
      vpc_security_group_ids=[bastion_sg.id],
      key_name=instance_key.key_name,
      user_data=user_data,
      tags={
          'Name': 'bastion-host',
          'Project': project_name
      }
  )
  
  
def create_rds_subnet_group(private_subnets):
  return rds.SubnetGroup(f'{project_name}-rds-subnet-group',
      subnet_ids=[
          private_subnets['a'].id,
          private_subnets['b'].id,
          private_subnets['c'].id
      ],
      tags={
          'Name': f'{project_name}-rds-subnet-group',
          'Project': project_name
      }
  )


def create_iam_eb_role():
    # Create the IAM role for Elastic Beanstalk
    role = iam.Role(f'{project_name}-eb-role',
        assume_role_policy="""{
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Action": "sts:AssumeRole",
                    "Principal": {
                        "Service": "ec2.amazonaws.com"
                    },
                    "Effect": "Allow",
                    "Sid": ""
                }
            ]
        }""",
        tags={
            'Name': f'{project_name}-eb-role',
            'Project': project_name
        }
    )

    # Attach the AWS Elastic Beanstalk service role policy
    iam.RolePolicyAttachment(f'{project_name}-eb-policy',
        role=role.name,
        policy_arn='arn:aws:iam::aws:policy/service-role/AWSElasticBeanstalkService'
    )
    
    return role
  
  
def create_iam_eb_instance_profile(role):
    return iam.InstanceProfile(f'{project_name}-eb-profile',
        role=role.name,
        tags={
            'Name': f'{project_name}-eb-profile',
            'Project': project_name
        }
    )


def configure_vpc():
  # Create the VPC and IGW
  vpc = create_vpc()
  igw = create_internet_gateway(vpc)
  
  # Create public subnets
  public_route_table = create_public_route_table(vpc, igw)
  public_subnets = create_public_subnets(vpc, route_table=public_route_table)
  
  # Create private subnets
  nat_gateway = create_nat_gateway(vpc, public_subnets['a'])
  private_route_table = create_private_route_table(vpc, nat_gateway)
  private_subnets = create_private_subnets(vpc, route_table=private_route_table)
  
  # Create security groups
  bastion_sg = create_bastion_sg(vpc)
  instance_sg = create_instance_sg(vpc, bastion_sg)
  rds_sg = create_rds_sg(vpc, bastion_sg)
  
  # Create bastion host
  instance_key = create_instance_key(vpc)
  bastion_host = create_bastion_host(public_subnets['a'], bastion_sg, instance_key)
  
  # Create subnet groups
  rds_subnet_group = create_rds_subnet_group(private_subnets)
  
  # Create IAM roles
  eb_role = create_iam_eb_role()
  eb_instance_profile = create_iam_eb_instance_profile(eb_role)
  
  return {
    'vpc': vpc,
    'subnets': {
      'public': public_subnets,
      'private': private_subnets
    },
    'security_groups': {
      'bastion': bastion_sg,
      'instance': instance_sg,
      'rds': rds_sg
    },
    'keys': {
      'instance': instance_key
    },
    'instances': {
      'bastion': bastion_host
    },
    'subnet_groups': {
      'rds': rds_subnet_group
    },
    'iam': {
      'roles': {
        'eb': eb_role,
      },
      'instance_profiles': {
        'eb': eb_instance_profile
      }
    }
  }
