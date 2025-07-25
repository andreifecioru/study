import requests
import json

from pydantic import BaseModel


OLLAMA_API_URL = 'http://localhost:11434/api/generate'
MODEL_NAME = 'mistral'


class Agent(BaseModel):
    name: str
    role: str
    description: str
    model: str = MODEL_NAME
    tools: list[dict] = []
    conversation_history: list[str] = []
    system_prompt: str = ''

    def __init__(self, **data):
        super().__init__(**data)
        self.conversation_history: list[str] = []
        if not self.system_prompt:
            self.system_prompt: str = f'''
                You are {self.name}, an AI agent. 
                Your role is {self.role}. 
                You describe yourself as: {self.description}.
                
                Here are the tools you have access to:
                {json.dumps([tool['meta'] for tool in  self.tools])}
                
                To use a tool, respond with a JSON object in the following format:
                {{ "tool_call": {{ "name": "tool_name", "parameters": {{ "arg1": "value1", "arg2": "value2" }} }} }}
                If you have the answer and cannot use a tool, respond directly to the user.
            '''

    def ask(self, prompt: str) -> str:
        headers = { 'Content-Type': 'application/json' }
        data = {
            'model': self.model,
            'prompt': f'{self.system_prompt}\n\n{prompt}',
            'stream': False
        }

        try:
            response = requests.post(OLLAMA_API_URL, headers=headers, data=json.dumps(data))
            response.raise_for_status()
            return response.json().get('response', 'No response generated.')
        except requests.exceptions.ConnectionError:
            return 'Error: Could not connect to Ollama server. Is it running?'
        except requests.exceptions.HTTPError as e:
            return f'Error: HTTP {e.response.status_code} - {e.response.text}'
        except json.JSONDecodeError:
            return 'Error: Could not decode JSON response from Ollama.'
        except Exception as e:
            return f'An unexpected error occurred: {e}'
