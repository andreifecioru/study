from rich.console import Console
from rich.theme import Theme
from pathlib import Path

from google.adk.agents import Agent

from .tools import refund_course


agent_theme = Theme({"agent": "orange1 bold"})
console = Console(theme=agent_theme)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


order_agent = Agent(
  name="order_agent",
  model="gemini-2.5-flash",
  description="Order agent for viewing purchase history and processing refunds",
  instruction=load_instructions(),
  tools=[refund_course],
)
