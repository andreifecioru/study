from google.adk.agents import Agent
from google.adk.tools import google_search
from pathlib import Path


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(encoding="utf-8")


news_analyst = Agent(
  name="news_analyst",
  model="gemini-2.5-flash",
  instruction=load_instructions(),
  tools=[google_search],
)
