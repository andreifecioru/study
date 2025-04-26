import pulumi as pm

from src.global_resources import create_global_resources
from src.backend import create_backend
from src.frontend import create_frontend


if __name__ == '__main__':
  org_name = pm.get_organization()  
  project_name = pm.get_project()
  stack_name = pm.get_stack()
  
  if stack_name == 'global':
    global_data = create_global_resources()
    
    for key, value in global_data.items():
      pm.export(key, value)
    
  else:
    global_data = pm.StackReference(f'{org_name}/{project_name}/global').outputs
    
    backend_data = create_backend(global_data)
    frontend_data = create_frontend(global_data, backend_data)
    
    for key, value in {**backend_data, **frontend_data}.items():
      pm.export(key, value)
    