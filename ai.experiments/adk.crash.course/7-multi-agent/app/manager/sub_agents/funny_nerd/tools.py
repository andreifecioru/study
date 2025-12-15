import yaml
from pathlib import Path
from google.adk.tools import ToolContext
from rich.console import Console
from rich.theme import Theme
from random import choice

tool_theme = Theme({"tool": "green bold"})
console = Console(theme=tool_theme)


def load_jokes() -> dict[str, list[str]]:
  file_contents = (Path(__file__).parent / "prompts" / "jokes.yaml").read_text(
    encoding="utf-8"
  )
  return yaml.safe_load(file_contents)


def get_nerd_joke(topic: str, tool_context: ToolContext) -> dict[str, str]:
  """
  Fetches a nerd-themed joke related to a specific topic.

  This function retrieves and returns a joke based on the provided topic. The
  joke is formatted and delivered as a dictionary with descriptive keys.

  Arguments:
      topic (str): The subject or theme about which the joke will be related.
      tool_context (ToolContext): Context required to access the session state.

  Returns:
      dict[str, str]: A dictionary containing the structure of the joke.
  """
  console.print(f"[tool](TOOL)[/] Telling a joke about {topic}")
  jokes = load_jokes()
  topic_jokes = jokes.get(topic.lower())

  if topic_jokes is None:
    console.print(
      f"[tool](TOOL)[/] Could not find joke about {topic}. Using the default joke"
    )
    topic = "default"
    topic_jokes = jokes.get(topic)

  joke = choice(topic_jokes)
  tool_context.state["nerd_last_joke_topic"] = topic.lower()

  return {
    "status": "success",
    "topic": topic,
    "joke": joke,
    "message": f"Found a joke about {topic}",
  }
