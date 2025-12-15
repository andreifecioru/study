from google.adk.agents import Agent
from google.adk.tools.agent_tool import AgentTool
from pathlib import Path

from .sub_agents.funny_nerd.agent import funny_nerd
from .sub_agents.news_analyst.agent import news_analyst
from .sub_agents.stock_analyst.agent import stock_analyst
from .tools import get_current_datetime


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(encoding="utf-8")


root_agent = Agent(
  name="manager",
  model="gemini-2.5-flash",
  description="Manager (root) agent",
  instruction=load_instructions(),
  sub_agents=[funny_nerd, stock_analyst],
  tools=[
    AgentTool(
      news_analyst
    ),  # agents that use built-in tools need to be wrapped inside an AgentTool
    get_current_datetime,
  ],
)
