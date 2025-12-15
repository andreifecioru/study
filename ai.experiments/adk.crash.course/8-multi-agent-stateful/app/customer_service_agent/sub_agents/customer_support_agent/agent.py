from rich.console import Console
from rich.theme import Theme
from pathlib import Path

from google.adk.agents import Agent

customer_agent_theme = Theme({"agent": "green bold"})
console = Console(theme=customer_agent_theme)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


customer_support_agent = Agent(
  name="customer_support_agent",
  model="gemini-2.5-flash",
  description="Course support agent for the AI Marketing Platform course",
  instruction=load_instructions(),
)
