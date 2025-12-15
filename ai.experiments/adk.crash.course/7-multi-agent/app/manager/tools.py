from datetime import UTC, datetime

from rich.console import Console
from rich.theme import Theme

tool_theme = Theme({"tool": "green bold"})
console = Console(theme=tool_theme)


def get_current_datetime() -> str:
  """
  Get the current datetime in ISO format (UTC).

  Returns:
    current datetime in ISO format (UTC)

  """
  console.print("[tool](TOOL)[/] Looking for the current time.")
  return datetime.now(tz=UTC).isoformat()
