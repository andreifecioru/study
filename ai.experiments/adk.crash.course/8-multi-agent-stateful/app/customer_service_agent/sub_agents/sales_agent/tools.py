from datetime import datetime, UTC
from google.adk.tools import ToolContext
from rich.console import Console
from rich.theme import Theme
from pathlib import Path
import yaml

tool_theme = Theme({"tool": "green on black bold"})
console = Console(theme=tool_theme)


def get_current_time() -> str:
  """
  Get the current time in ISO format (UTC).

  Returns:
    str: current datetime in ISO format (UTC)
  """
  return datetime.now(tz=UTC).isoformat()


def list_available_courses() -> dict[str, dict[str, str]]:
  """
  Retrieves the complete list of available courses.

  Returns:
    dict: the full list of available courses and associated info in dictionary format
  """
  console.print("[tool](TOOL)[/] Retrieving the list of available courses...")
  file_content = (Path(__file__).parent / "prompts" / "courses.yaml").read_text(
    encoding="utf-8"
  )
  return yaml.safe_load(file_content)


def purchase_course(course_id: str, tool_context: ToolContext) -> dict[str, str]:
  """
  Purchase a course with the specified ID.
  The purchase takes place only if the specified course has **not** been previously purchased.

  Args:
    course_id (str): the ID of the course to be refunded
    tool_context (ToolContext): Context required to access the session state.

  Returns:
    dict[str, str]: A confirmation message
  """
  console.print(f"[tool](TOOL)[/] Refunding course: {course_id}")
  purchased_courses = tool_context.state.get("purchased_courses", [])
  purchased_courses_ids = [course["id"] for course in purchased_courses]

  if course_id in purchased_courses_ids:
    return {
      "status": "error",
      "message": f"Course with ID {course_id} has already been purchased. Cannot purchase again.",
    }

  # Update the state
  purchased_courses.append({"id": course_id, "timestamp": get_current_time()})
  tool_context.state["purchased_courses"] = purchased_courses

  history = tool_context.state.get("interaction_history", [])
  history.append(
    {
      "action": "purchase_course",
      "course_id": course_id,
      "timestamp": get_current_time(),
    }
  )
  tool_context.state["interaction_history"] = history

  return {
    "status": "success",
    "course_id": course_id,
    "message": f"Purchased the {course_id} course",
  }
