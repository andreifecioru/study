from rich.console import Console
from rich.theme import Theme
from pathlib import Path

from google.adk.agents import Agent


agent_theme = Theme({"agent": "medium_purple bold"})
console = Console(theme=agent_theme)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


policy_agent = Agent(
  name="policy_agent",
  model="gemini-2.5-flash",
  description="Policy agent for the AI Developer Accelerator community",
  instruction=load_instructions(),
)
