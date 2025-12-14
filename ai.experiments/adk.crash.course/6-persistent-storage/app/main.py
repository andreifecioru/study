from typing import Any

import anyio
import questionary
from anyio.to_thread import run_sync
from dotenv import load_dotenv
from google.adk.runners import Runner
from google.adk.sessions import Session
from google.adk.sessions.database_session_service import DatabaseSessionService
from reminders_agent import reminders_agent
from rich.console import Console
from rich.theme import Theme
from rich.traceback import install
from utils import call_agent

load_dotenv()
install()


APP_NAME = "Persistent Memory Agent"
USER_ID = "andrei.fecioru"
INITIAL_STATE: dict[str, Any] = {"user_name": "Andrei Fecioru", "reminders": []}

agent_theme = Theme({"agent": "blue bold"})
console = Console(theme=agent_theme)


async def get_or_create_session(session_service: DatabaseSessionService) -> Session:
  list_session_response = await session_service.list_sessions(
    app_name=APP_NAME, user_id=USER_ID
  )
  existing_sessions = list_session_response.sessions
  if existing_sessions and len(existing_sessions) > 0:
    session = existing_sessions[0]
    # Use the most recent session
    console.print(f"[agent][AGENT][/] Restoring existing session: {session.id}")
    return session

  new_session = await session_service.create_session(
    app_name=APP_NAME,
    user_id=USER_ID,
    state=INITIAL_STATE,
  )
  console.print(f"[agent][AGENT][/] Created a new session: {new_session.id}")
  return new_session


def get_user_input(question_text: str) -> str:
  """A synchronous function that blocks for user input."""
  question = questionary.text(question_text)
  # The 'unsafe_ask()' or 'ask()' method is synchronous and blocking
  answer = question.ask()
  return answer


async def main() -> None:
  db_url = "sqlite+aiosqlite:///./agent_data.db"
  session_service = DatabaseSessionService(db_url=db_url)
  session = await get_or_create_session(session_service)
  runner = Runner(
    agent=reminders_agent, app_name=APP_NAME, session_service=session_service
  )

  try:
    while True:
      user_input = await run_sync(lambda: get_user_input("You: "))
      if user_input.lower() in ("exit", "quit"):
        console.print(
          "[agent][AGENT][/] Ending conversation. Your data has been saved to the DB."
        )
        break

      # Process the query via the agent
      await call_agent(runner, USER_ID, session.id, user_input)
  finally:
    console.print("[agent][AGENT][/] Shutting down. Please wait...")
    await runner.close()
    await session_service.db_engine.dispose()


if __name__ == "__main__":
  anyio.run(main, backend="asyncio")
