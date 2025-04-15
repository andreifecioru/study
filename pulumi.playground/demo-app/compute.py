import pulumi
from pulumi_aws import ec2, rds, elasticbeanstalk as eb, iam

project_name = pulumi.get_project()
stack_name = pulumi.get_stack()


def create_private_ec2_instance(vpc_data):
  # User data script to update the system
  with open('./resources/scripts/update_packages.sh', 'r') as f:
    user_data = f.read() 
    
  return ec2.Instance(f'{project_name}-{stack_name}-private-instance',
      instance_type='t2.micro',
      ami='ami-0669b163befffbdfc',  # Amazon Linux 2023 AMI in eu-central-1
      vpc_security_group_ids=[vpc_data['security_groups']['instance'].id],
      subnet_id=vpc_data['subnets']['private']['a'].id,
      user_data=user_data,
      key_name=vpc_data['keys']['instance'].key_name,
      tags={
          'Name': f'{project_name}-{stack_name}-private-instance',
          'Project': project_name,
          'Environment': stack_name
      }
  )
  