import pulumi
from pulumi_aws import rds, elasticbeanstalk as eb

from utils import eb_env_settings

project_name = pulumi.get_project()
stack_name = pulumi.get_stack()
aws_config = pulumi.Config("aws")
aws_region = aws_config.require("region")


def create_rds_instance(global_data):
    return rds.Instance(
        f'{project_name}-{stack_name}-rds',
        engine='postgres',
        engine_version='17.2',
        instance_class='db.t3.small',
        allocated_storage=10,
        db_name=f'{project_name}-db'.replace('-', ''),
        username='dbadmin',
        password='LockBox#1121',  # Consider using AWS Secrets Manager
        vpc_security_group_ids=[
            global_data['security_groups.rds.id'],
            global_data['security_groups.bastion.id']
        ],
        db_subnet_group_name=global_data['subnet_groups.rds.name'],
        skip_final_snapshot=True,
        tags={
            'Name': f'{project_name}-{stack_name}-rds',
            'Project': project_name,
            'Environment': stack_name
        }
    )


def create_eb_environment(global_data, rds_instance):
    private_subnets = [
        global_data['subnets.private.a.id'],
        global_data['subnets.private.b.id'],
        global_data['subnets.private.c.id']
    ]
    private_subnet_ids = pulumi.Output.concat(','.join(private_subnets))

    public_subnets = [
        global_data['subnets.public.a.id'],
        global_data['subnets.public.b.id'],
        global_data['subnets.public.c.id']
    ]
    public_subnet_ids = pulumi.Output.concat(','.join(public_subnets))

    return eb.Environment(
        f'{project_name}-backend-{stack_name}',
        name=f'{project_name}-backend-{stack_name}',
        application=global_data['applications.eb.name'],
        solution_stack_name='64bit Amazon Linux 2023 v4.5.0 running Corretto 17',
        settings=eb_env_settings({
            'aws:autoscaling:launchconfiguration>IamInstanceProfile': global_data[
                'iam.instance_profiles.eb.name'],
            'aws:autoscaling:launchconfiguration>InstanceType': 't3.small',
            'aws:autoscaling:launchconfiguration>SecurityGroups': global_data[
                'security_groups.instance.id'],
            'aws:autoscaling:launchconfiguration>EC2KeyName': global_data['keys.instance.key_name'],
            'aws:autoscaling:launchconfiguration>ImageId': 'ami-0e994c5a8caa6aa32',
            'aws:autoscaling:launchconfiguration>RootVolumeType': 'gp3',
            'aws:autoscaling:launchconfiguration>RootVolumeSize': '10',
            'aws:autoscaling:launchconfiguration>RootVolumeIOPS': '3000',
            'aws:autoscaling:launchconfiguration>RootVolumeThroughput': '125',
            'aws:elasticbeanstalk:environment>EnvironmentType': 'LoadBalanced',
            'aws:elasticbeanstalk:environment:loadbalancer>LoadBalancerType': 'application',
            'aws:autoscaling:asg>MinSize': '1',
            'aws:autoscaling:asg>MaxSize': '1',
            'aws:ec2:vpc>VPCId': global_data['vpc.id'],
            'aws:ec2:vpc>Subnets': private_subnet_ids,
            'aws:ec2:vpc>ELBSubnets': public_subnet_ids,
            'aws:elasticbeanstalk:application:environment>DB_HOST': rds_instance.endpoint,
            'aws:elasticbeanstalk:application:environment>DB_NAME': rds_instance.db_name,
            'aws:elasticbeanstalk:application:environment>DB_USER': rds_instance.username,
            'aws:elasticbeanstalk:application:environment>DB_PASSWORD': rds_instance.password,
            'aws:elasticbeanstalk:application:environment>PORT': '8080',
            'aws:elasticbeanstalk:application:environment>SPRING_PROFILES_ACTIVE': stack_name,
            'aws:elasticbeanstalk:environment:process:default>Port': '8080',
            'aws:elasticbeanstalk:environment:process:default>HealthCheckPath': '/api/v1/health',
            'aws:elasticbeanstalk:environment:process:default>HealthCheckInterval': '30',
            'aws:elasticbeanstalk:environment:process:default>HealthCheckTimeout': '5',
            'aws:elasticbeanstalk:environment:process:default>HealthyThresholdCount': '3',
            'aws:elasticbeanstalk:environment:process:default>UnhealthyThresholdCount': '5'
        }),
        tags={
            'Name': f'{project_name}-env',
            'Project': project_name,
            'Environment': stack_name
        }
    )


def create_backend(global_data):
    rds_instance = create_rds_instance(global_data)
    eb_environment = create_eb_environment(global_data, rds_instance)

    return {
        'rds.instance.endpoint': rds_instance.endpoint,
        'eb.environment.cname': eb_environment.cname,
        'eb.environment.endpoint_url': eb_environment.endpoint_url,
    }
