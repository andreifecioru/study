import pulumi
from pulumi_aws import ec2, rds, elasticbeanstalk as eb, iam

project_name = pulumi.get_project()
stack_name = pulumi.get_stack()
aws_config = pulumi.Config("aws")
aws_region = aws_config.require("region")


def create_application():
  return eb.Application(f'{project_name}-backend',
      name=f'{project_name}-backend',
      description='Elastic Beanstalk application for the DemoApp project',
      tags={
          'Name': f'{project_name}-backend',
          'Project': project_name
      }
  )
  

def create_rds_instance(vpc_data):
  return rds.Instance(f'{project_name}-{stack_name}-rds',
      engine='postgres',
      instance_class='db.t3.small',
      allocated_storage=10,
      db_name='appdb',
      username='dbadmin',
      password='LockBox#1121',  # Consider using AWS Secrets Manager
      vpc_security_group_ids=[vpc_data['security_groups']['rds'].id, vpc_data['security_groups']['bastion'].id],
      db_subnet_group_name=vpc_data['subnet_groups']['rds'].name,
      skip_final_snapshot=True,
      tags={
          'Name': f'{project_name}-{stack_name}-rds',
          'Project': project_name,
          'Environment': stack_name
      }
  )


def create_environment(vpc_data, application, rds_instance):
    instance_profile = vpc_data['iam']['instance_profiles']['eb']

    private_subnet_ids = pulumi.Output.all(
        vpc_data['subnets']['private']['a'].id,
        vpc_data['subnets']['private']['b'].id,
        vpc_data['subnets']['private']['c'].id
    ).apply(lambda ids: ','.join(ids))

    public_subnet_ids = pulumi.Output.all(
        vpc_data['subnets']['public']['a'].id,
        vpc_data['subnets']['public']['b'].id,
        vpc_data['subnets']['public']['c'].id
    ).apply(lambda ids: ','.join(ids))
    
    return eb.Environment(f'{project_name}-backend-{stack_name}',
        name=f'{project_name}-backend-{stack_name}',
        application=application.name,
        solution_stack_name='64bit Amazon Linux 2023 v4.5.0 running Corretto 17',
        settings=[
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='IamInstanceProfile',
                value=instance_profile.name
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='InstanceType',
                value='t3.small'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='SecurityGroups',
                value=vpc_data['security_groups']['instance'].id
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='RootVolumeType',
                value='gp3'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='RootVolumeSize',
                value='10'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='RootVolumeIOPS',
                value='3000'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:launchconfiguration',
                name='RootVolumeThroughput',
                value='125'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:environment',
                name='EnvironmentType',
                value='LoadBalanced'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:environment:loadbalancer',
                name='LoadBalancerType',
                value='application'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:asg',
                name='MinSize',
                value='1'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:autoscaling:asg',
                name='MaxSize',
                value='1'
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:ec2:vpc',
                name='VPCId',
                value=vpc_data['vpc'].id
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:ec2:vpc',
                name='Subnets',
                value=private_subnet_ids
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:ec2:vpc',
                name='ELBSubnets',
                value=public_subnet_ids
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:application:environment',
                name='DB_HOST',
                value=rds_instance.endpoint
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:application:environment',
                name='DB_NAME',
                value=rds_instance.db_name
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:application:environment',
                name='DB_USER',
                value=rds_instance.username
            ),
            eb.EnvironmentSettingArgs(
                namespace='aws:elasticbeanstalk:application:environment',
                name='DB_PASSWORD',
                value=rds_instance.password
            )
        ],
        tags={
            'Name': f'{project_name}-env',
            'Project': project_name,
            'Environment': stack_name
        }
    )
  
  
def create_backend(vpc_data):
  application = create_application()
  rds_instance = create_rds_instance(vpc_data)
  environment = create_environment(vpc_data, application, rds_instance)
  