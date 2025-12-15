from rich.console import Console
from rich.theme import Theme
from pathlib import Path

from google.adk.agents import Agent

from .sub_agents.customer_support_agent import customer_support_agent
from .sub_agents.order_agent import order_agent
from .sub_agents.policy_agent import policy_agent
from .sub_agents.sales_agent import sales_agent


customer_agent_theme = Theme({"agent": "blue bold"})
console = Console(theme=customer_agent_theme)


def load_instructions() -> str:
  return (Path(__file__).parent / "prompts" / "instructions.md").read_text(
    encoding="utf-8"
  )


customer_service_agent = Agent(
  name="customer_service_agent",
  model="gemini-2.5-flash",
  description="Customer service agent for AI Developer Accelerator community.",
  instruction=load_instructions(),
  sub_agents=[customer_support_agent, order_agent, policy_agent, sales_agent],
)
