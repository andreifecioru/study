import warnings

import asyncio
import os
import fastmcp
from dotenv import load_dotenv
import questionary as qs
from chat_bot import ChatBot

load_dotenv()
GEMINI_API_KEY = os.getenv('GEMINI_API_KEY')
if not GEMINI_API_KEY:
    raise ValueError('GEMINI_API_KEY is not set')


async def main():
    chatbot = ChatBot(
        name="Chad Bot",
        api_key=GEMINI_API_KEY,
        mcp_clients=[
            fastmcp.Client("http://127.0.0.1:8000/mcp")
        ]
    )

    while True:
        question = qs.text("").ask()
        if question in ("quit", "q"):
            print("Bye!")
            break

        if not question:
            continue

        print("🤖 Thinking...")
        response = await chatbot.ask(question)
        print(f'🤖 {response}')


if __name__ == '__main__':
    warnings.filterwarnings("ignore", message=".*non-text parts in the response.*")

    asyncio.run(main())