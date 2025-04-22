import pulumi
from pulumi_aws import elasticbeanstalk as eb


def eb_env_settings(settings):
  settings_list = []
  
  for key, value in settings.items():
    (namespace, name) = key.split('>')
    
    settings_list.append(eb.EnvironmentSettingArgs(
      namespace=namespace,
      name=name,
      value=value
    ))
    
  return settings_list
    