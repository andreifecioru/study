from typing import Any

import anyio
import questionary
from anyio.to_thread import run_sync
from dotenv import load_dotenv
from google.adk.runners import Runner
from google.adk.sessions import Session
from google.adk.sessions.database_session_service import DatabaseSessionService
from customer_service_agent import customer_service_agent
from rich.console import Console
from rich.theme import Theme
from rich.traceback import install
from utils import call_agent

load_dotenv()
install()


APP_NAME = "Customer Service AI Agent"
USER_ID = "andrei.fecioru"
INITIAL_STATE: dict[str, Any] = {
  "user_name": "Andrei Fecioru",
  "purchased_courses": [],
  "interaction_history": []
}

app_theme = Theme({"app": "gold1 bold"})
console = Console(theme=app_theme)


async def get_or_create_session(session_service: DatabaseSessionService) -> Session:
  list_session_response = await session_service.list_sessions(
    app_name=APP_NAME, user_id=USER_ID
  )
  existing_sessions = list_session_response.sessions
  if existing_sessions and len(existing_sessions) > 0:
    session = existing_sessions[0]
    # Use the most recent session
    console.print(f"[app][APP][/] Restoring existing session: {session.id}")
    return session

  new_session = await session_service.create_session(
    app_name=APP_NAME,
    user_id=USER_ID,
    state=INITIAL_STATE,
  )
  console.print(f"[app][APP][/] Created a new session: {new_session.id}")
  return new_session


def get_user_input(question_text: str) -> str:
  """A synchronous function that blocks for user input."""
  question = questionary.text(question_text)
  # The 'unsafe_ask()' or 'ask()' method is synchronous and blocking
  return question.ask()


async def main() -> None:
  db_url = "sqlite+aiosqlite:///./customer_agent_data.db"
  session_service = DatabaseSessionService(db_url=db_url)
  session = await get_or_create_session(session_service)
  runner = Runner(
    agent=customer_service_agent, app_name=APP_NAME, session_service=session_service
  )

  try:
    while True:
      user_input = await run_sync(lambda: get_user_input("You: "))
      if user_input.lower() in ("exit", "quit"):
        console.print(
          "[app][APP][/] Ending conversation. Your data has been saved to the DB."
        )
        break

      # Process the query via the agent
      await call_agent(runner, USER_ID, session, user_input)
  finally:
    console.print("[app][APP][/] Shutting down. Please wait...")
    await runner.close()
    await session_service.db_engine.dispose()


if __name__ == "__main__":
  anyio.run(main, backend="asyncio")
