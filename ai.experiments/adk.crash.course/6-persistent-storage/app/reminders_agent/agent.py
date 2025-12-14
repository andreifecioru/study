from pathlib import Path

from google.adk.agents import Agent

from .tools import (
  add_reminder,
  delete_reminder,
  list_reminders,
  update_reminder,
  update_user_name,
  view_reminder,
)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


reminders_agent = Agent(
  name="reminders_agent",
  model="gemini-2.5-flash",
  description="A smart reminder agent with persistent memory",
  instruction=load_instructions(),
  tools=[
    add_reminder,
    view_reminder,
    update_user_name,
    list_reminders,
    update_reminder,
    delete_reminder,
  ],
)
