from .agent import Agent
from tools import TOOLS


class ChatBot(Agent):
    def __init__(self, name: str, description: str):
        super().__init__(
            name=name,
            role='chat-bot',
            description=description,
            tools = [TOOLS['calculator']]
        )
