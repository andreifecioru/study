from litellm import completion


class AgentOne:
    OLLAMA_API_BASE = 'http://localhost:11434'
    MODEL_NAME = 'mistral'

    SYSTEM_PROMPT = '''
    You are an AI agent that can perform tasks by using available tools.

    Available tools:
    - list_files() -> List[str]: List all files in the target directory.
    - read_file(file_name: str) -> str: Read the content of a file.
    - terminate(message: str): End the agent loop and print a summary to the user.

    If a user asks about files, list them before reading.

    Every response MUST have an action.
    Respond in this format:

    ```action
    {
        "tool_name": "insert tool_name",
        "args": {...fill in any required arguments here...}
    }````
    
    If you are not sure what to do, issue a terminate action.
    '''

    def __init__(self):
        self.name = 'AgentOne'
        self.description = 'This is Agent v1.'
        self.rules = [{ 'role': 'system', 'content': AgentOne.SYSTEM_PROMPT }]
        self.memory = self.rules[:]

    def generate_response(self, user_prompt: str, print_usage: bool = False) -> str:
        self.memory.append({'role': 'user', 'content': user_prompt})

        response = {}
        if AgentOne.MODEL_NAME == 'gemini':
            response = completion(
                model='gemini/gemini-2.5-flash-lite',
                messages=self.memory,
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
        elif AgentOne.MODEL_NAME == 'mistral':
            response = completion(
                model='ollama/mistral',
                api_base=AgentOne.OLLAMA_API_BASE,
                messages=self.memory,
            )

        if print_usage:
            print(response.get('usage', {}))
            
        if not response.get('choices'):
            print(f'No response from the model: {response}')
            return 'No response from the model.'
        else:
            content = response['choices'][0]['message']['content']
            self.memory.append({'role': 'assistant', 'content': content})
            return content
