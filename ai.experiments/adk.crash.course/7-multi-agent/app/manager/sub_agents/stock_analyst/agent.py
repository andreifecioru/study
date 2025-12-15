from pathlib import Path

from google.adk.agents import Agent

from .tools import get_stock_price


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(encoding="utf-8")


stock_analyst = Agent(
  name="stock_analyst",
  model="gemini-2.5-flash",
  description="An agent that can loop up stock prices and track them over time",
  instruction=load_instructions(),
  tools=[get_stock_price],
)