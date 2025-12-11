import uuid

import anyio
from chat_bot import chat_bot_agent
from dotenv import load_dotenv
from google.adk.runners import Runner
from google.adk.sessions import InMemorySessionService, Session
from google.genai import types

APP_NAME = "ChatBot"
USER_ID = "andrei_fecioru"
SESSION_ID = str(uuid.uuid4())


async def main() -> None:
  initial_state = {
    "user_name": "Fecioru Andrei",
    "user_preferences": """
      I like to play computer games.
      My favorite food is stake.
      I like to go to the gym almost every day.
      I try to read as musch as I can in my spare time.
    """,
  }

  # create a new session
  session_service = InMemorySessionService()
  session: Session | None = await session_service.create_session(
    app_name=APP_NAME, user_id=USER_ID, session_id=SESSION_ID, state=initial_state
  )
  print("CREATED A NEW SESSION")
  print(f"  - session ID: {SESSION_ID}")

  # create the runner
  runner = Runner(
    agent=chat_bot_agent,
    app_name=APP_NAME,
    session_service=session_service,
  )

  new_message = types.Content(
    role="user", parts=[types.Part(text="What is Andrei's favorite activity?")]
  )

  for event in runner.run(
    user_id=USER_ID, session_id=SESSION_ID, new_message=new_message
  ):
    if event.is_final_response() and event.content and event.content.parts:
      final_response = event.content.parts[0].text
      print(f"Final response: {final_response}")

  print("\n====== Session information =====")
  session = await session_service.get_session(
    app_name=APP_NAME, user_id=USER_ID, session_id=SESSION_ID
  )

  if session:
    for key, value in session.state.items():
      print(f"  {key}: {value}")


if __name__ == "__main__":
  load_dotenv()

  anyio.run(main)
