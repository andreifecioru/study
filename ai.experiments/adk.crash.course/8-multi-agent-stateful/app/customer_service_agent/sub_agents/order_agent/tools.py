from datetime import datetime, UTC
from rich.console import Console
from rich.theme import Theme
from google.adk.tools import ToolContext

tool_theme = Theme({"tool": "green on black bold"})
console = Console(theme=tool_theme)


def get_current_time() -> str:
  """
  Get the current time in ISO format (UTC).

  Returns:
    str: current datetime in ISO format (UTC)
  """
  return datetime.now(tz=UTC).isoformat()


def refund_course(course_id: str, tool_context: ToolContext) -> dict[str, str]:
  """
  Refunds a course with the specified ID.
  The refund takes place only if the specified course has been previously purchased.

  Args:
    course_id (str): the ID of the course to be refunded
    tool_context (ToolContext): Context required to access the session state.

  Returns:
    dict[str, str]: A confirmation message
  """
  console.print(f"[tool](TOOL)[/] Refunding course: {course_id}")
  purchased_courses = tool_context.state.get("purchased_courses", [])
  purchased_courses_ids = [course["id"] for course in purchased_courses]

  if course_id not in purchased_courses_ids:
    return {
      "status": "error",
      "message": f"Course with ID {course_id} has not yet been purchased. Cannot refund.",
    }

  # Update the state
  remaining_courses = [
    course for course in purchased_courses if course["id"] != course_id
  ]
  tool_context.state["purchased_coursed"] = remaining_courses

  history = tool_context.state.get("interaction_history", [])
  history.append(
    {"action": "refund_course", "course_id": course_id, "timestamp": get_current_time()}
  )
  tool_context.state["interaction_history"] = history

  return {
    "status": "success",
    "course_id": course_id,
    "message": f"Course with ID {course_id} has been refunded",
  }
