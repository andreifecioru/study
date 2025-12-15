from google.adk.agents import Agent
from pathlib import Path
from .tools import get_nerd_joke


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(encoding="utf-8")


funny_nerd = Agent(
  name="funny_nerd",
  model="gemini-2.5-flash",
  description="An agent that tells jokes about various topics.",
  instruction=load_instructions(),
  tools=[get_nerd_joke],
)
