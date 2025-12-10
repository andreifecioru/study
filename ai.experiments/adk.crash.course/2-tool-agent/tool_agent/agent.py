from datetime import UTC, datetime

from google.adk.agents import Agent
from google.adk.tools.google_search_tool import GoogleSearchTool


def get_current_time() -> dict[str, str]:
  """
  Get the current time using format: YYYY-mm-DD HH:MM:SS.

  :return: The current time in format YYYY-mm-DD HH:MM:SS
  :rtype: dict
  """
  return {
    "current_time": datetime.now(UTC).strftime("%Y-%m-%d %H:%M:%S"),
  }


google_search_tool = GoogleSearchTool()

root_agent = Agent(
  name="tool_agent",
  model="gemini-2.5-flash",
  description="An agent capable of using various tools",
  instruction="""
    You are a helpful assistant with various tools at your disposal.
    These tools are:
        - get the current time
        - google search
    """,
  tools=[google_search_tool],
)
