from rich.console import Console
from rich.theme import Theme
from pathlib import Path

from google.adk.agents import Agent

from .tools import list_available_courses, purchase_course


agent_theme = Theme({"agent": "light_pink3 bold"})
console = Console(theme=agent_theme)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


sales_agent = Agent(
  name="sales_agent",
  model="gemini-2.5-flash",
  description="Sales agent for the AI Marketing Platform course",
  instruction=load_instructions(),
  tools=[list_available_courses, purchase_course]
)
