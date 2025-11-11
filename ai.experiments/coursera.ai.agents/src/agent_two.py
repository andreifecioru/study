from litellm import completion
from typing import Any, List, Dict, Tuple


class AgentTwo:
    OLLAMA_API_BASE = 'http://localhost:11434'
    MODEL_NAME = 'ollama'

    SYSTEM_PROMPT = '''
You are an AI agent that can perform tasks by using available tools.

IMPORTANT: You have full access to the current working directory AND ALL of its subdirectories.
- You can list files in ANY subdirectory (e.g., './output', './src/utils', etc.)
- You can read files from ANY subdirectory using relative paths from the current directory
- Paths like './output/file.py' are completely valid and allowed

Workflow:
1. If a user asks about files, list them first before reading
2. When you want to read a file, provide its full relative path from the current directory
3. When you have gathered all necessary information, call the terminate tool with a comprehensive summary

Every response MUST call exactly one of the available tools.
'''

    def __init__(self, tools: List[Dict[str, Any]]):
        self.name = 'AgentTwo'
        self.description = 'This is Agent v1.'
        self.rules = [{ 'role': 'system', 'content': AgentTwo.SYSTEM_PROMPT }]
        self.memory = self.rules[:]
        self.tools = tools
        
    def user_prompt(self, user_prompt: str, print_usage: bool = False) -> Tuple[str, Any]:
        self.memory.append({'role': 'user', 'content': user_prompt})
        return self.__generate_response(print_usage)
    
    def tool_response(self, tool_response: str, tool_call_id: str, print_usage: bool = False) -> Tuple[str, Any]:
        self.memory.append({'role': 'tool', 'content': tool_response, 'tool_call_id': tool_call_id})
        return self.__generate_response(print_usage)
    
    def __generate_response(self, print_usage: bool = False) -> Tuple[str, Any]:
        retries_left = 3
        while retries_left > 0:
            try:
                return self.__invoke_model(print_usage)
            except ValueError as e:
                print(f'Error: {e}')
                retries_left -= 1
                if retries_left > 0:
                    print(f'Retrying... ({retries_left} retries left)')
                    
        raise ValueError('Model faled to respond in a valid format.')
        
    def __invoke_model(self, print_usage: bool = False) -> Tuple[str, Any]:
        response = {}
        if AgentTwo.MODEL_NAME == 'gemini':
            response = completion(
                model='gemini/gemini-2.5-flash-lite',
                messages=self.memory,
                tools=self.tools,
                extra_body={
                    'thinking_config': {
                        # Setting this to 0 should disable the reasoning process.
                        'thinking_budget': 0
                    },
                    'generation_config': {
                        'max_output_tokens': 2048,
                        'reasoning_effort': 'low'
                    }
                }
            )
        elif AgentTwo.MODEL_NAME == 'ollama':
            print(f'Memory:')
            for message in self.memory:
                print(f'{message["role"]}: {message["content"]}')
                if message.get('tool_call_id'):
                    print(f'Tool call ID: {message["tool_call_id"]}')
            print('--------------------------------')
            response = completion(
                model='ollama/llama3.1:8b',
                api_base=AgentTwo.OLLAMA_API_BASE,
                messages=self.memory,
                tools=self.tools,
            )

        if print_usage:
            print(response.get('usage', {}))
           
        if not response.get('choices'):
            print(f'No response from the model: {response}')
            raise ValueError('No response from the model.')
        elif not response['choices'][0]['message'].get('tool_calls'):
            print(f'No tool calls in the response: {response}')
            raise ValueError('No tool calls in the response.')
        else:
            print(f'Response: {response}')
            content = response['choices'][0]['message']['content']
            tool_calls = response['choices'][0]['message']['tool_calls']
            self.memory.append({'role': 'assistant', 'content': content, 'tool_calls': tool_calls})
            return content, tool_calls[0]
