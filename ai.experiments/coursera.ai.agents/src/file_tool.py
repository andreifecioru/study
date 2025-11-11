import os
import json
from typing import List, Tuple, Dict, Any

class BaseFileTool:
  def __init__(self):
    self.name = 'file_tool'
    self.description = 'A tool that can manipulate files.'
    self.commands = {
      'list_files': self.__list_files,
      'read_file': self.__read_file,
      'terminate': self.__terminate,
    }

  def __list_files(self, directory: str = './') -> str:
    print(f'Listing files in {directory}...')
    return f'Files in {directory}:\n' + '\n'.join(os.listdir(directory))
    
  def __terminate(self, message: str) -> str:
    print(f'Terminating with message: {message}')
    return message
  
  def __read_file(self, file_name: str) -> str:
    print(f'Reading file: {file_name}')
    return open(file_name, 'r').read()

  
class FileTool_v1(BaseFileTool):
  def __init__(self):
    super().__init__()
    self.name = 'FileTool (v1)'
      
  def execute(self, command: str) -> str:
    action, args = self.__parse_action(command)
    print(f'Executing command: {action} with args: {args}')
    return self.commands[action](**args)

  def __parse_action(self, action: str) -> Tuple[str, Dict[str, str]]:
    action_block = action.strip().split('```action')[1].removeprefix('```action').removesuffix('```')
    try:
      action_json = json.loads(action_block)
    except json.JSONDecodeError as e:
      print(f'Error parsing action {action}')
      raise e
    else:
      return action_json['tool_name'], action_json['args']
    
    
class FileTool_v2(BaseFileTool):
  def __init__(self):
    super().__init__()
    self.name = 'FileTool (v2)'
    
  def execute(self, tool_call: Any) -> str:
    function_name = tool_call['function']['name']
    function_args = json.loads(tool_call['function']['arguments'])
    print(f'Executing function: {function_name} with args: {function_args}')
    return self.commands[function_name](**function_args)
  
  
def call_tool_v1() -> str:
  file_tool = FileTool_v1()
  action = """```action
{
    "tool_name": "list_files",
    "args": {
        "directory": "./output"
    }
}
```"""

  return file_tool.execute(action)


if __name__ == '__main__':
  print(os.getcwd())
  tool_response = call_tool_v1()
  print(tool_response)