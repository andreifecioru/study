from google.adk.tools.tool_context import ToolContext
from rich.console import Console
from rich.theme import Theme

tool_theme = Theme({"tool": "green bold"})
console = Console(theme=tool_theme)

# NOTE: the last parameter to your tools **must** be tool_context: ToolContext (that name exactly)

def add_reminder(reminder: str, tool_context: ToolContext) -> dict[str, str]:
  """
  Add a new reminder to the user's reminder list.

  :param reminder: The new reminder to be added
  :type reminder: str
  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str]
  """
  console.print(f"[tool][TOOL][/] Adding reminder: {reminder}")

  # Append the new reminder to the current state
  reminders = tool_context.state.get("reminders", [])
  reminders.append(reminder)
  tool_context.state["reminders"] = reminders

  return {
    "action": "add_reminder",
    "reminder": reminder,
    "message": f"Reminder added: {reminder}",
  }


def view_reminder(index: int, tool_context: ToolContext) -> dict[str, str | int]:
  """
  View the text of the nth reminder.

  :param index: The 1-based index of the reminder that needs viewing.
  :type index: int
  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str | int]
  """
  console.print(f"[tool][TOOL][/] View reminder with index: {index}")

  # Extract the reminders from the current state
  reminders = tool_context.state.get("reminders", [])
  reminder_count = len(reminders)

  if index < 1 or index > reminder_count:
    return {
      "action": "view_reminder",
      "status": "error",
      "message": f"Invalid reminder index: ({index}). Must be in interval [1, {reminder_count}]",
    }

  reminder = reminders[index - 1]
  return {
    "action": "view_reminder",
    "index": index,
    "reminder": reminder,
    "message": f"Retrived reminder at index {index}",
  }


def list_reminders(tool_context: ToolContext) -> dict[str, str | list[str]]:
  """
  List the current reminders.

  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: Message containing the list of current reminders.
  :rtype: dict[str, str | list[str]]
  """
  console.print("[tool][TOOL][/] Listing existing reminders")

  # Extract the reminders from the current state
  reminders = tool_context.state.get("reminders", [])

  return {
    "action": "list_reminders",
    "reminders": reminders,
    "message": f"Found {len(reminders)} reminders: {reminders}",
  }


def update_reminder(
  index: int, reminder: str, tool_context: ToolContext
) -> dict[str, str | int]:
  """
  Update the text of the nth reminder.

  :param index: The 1-based index of the reminder that needs updating.
  :type index: int
  :param reminder: The updated text of the remeninder.
  :type reminder: str
  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str | int]
  """
  console.print(f"[tool][TOOL][/] Updating reminder with index: {index}")

  # Extract the reminders from the current state
  reminders = tool_context.state.get("reminders", [])
  reminder_count = len(reminders)

  if index < 1 or index > reminder_count:
    return {
      "action": "update_reminder",
      "status": "error",
      "message": f"Invalid reminder index: ({index}). Must be in interval [1, {reminder_count}]",
    }

  # Update the state
  _index = index - 1
  old_reminder = reminders[_index]
  reminders[_index] = reminder

  tool_context.state["reminders"] = reminders

  return {
    "action": "update_reminder",
    "index": index,
    "old_text": old_reminder,
    "updated_text": reminder,
    "message": f"Updated reminder at index {index}",
  }


def delete_reminder(index: int, tool_context: ToolContext) -> dict[str, str | int]:
  """
  Delete the nth reminder.

  :param index: The 1-based index of the reminder that needs deleting.
  :type index: int
  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str | int]
  """
  console.print(f"[tool][TOOL][/] Deleting reminder with index: {index}")

  # Extract the reminders from the current state
  reminders = tool_context.state.get("reminders", [])
  reminder_count = len(reminders)

  if index < 1 or index > reminder_count:
    return {
      "action": "delete_reminder",
      "status": "error",
      "message": f"Invalid reminder index: ({index}). Must be in interval [1, {reminder_count}]",
    }

  # Update the state
  deleted_reminder = reminders.pop(index - 1)
  tool_context.state["reminders"] = reminders

  return {
    "action": "delete_reminder",
    "index": index,
    "deleted_reminder": deleted_reminder,
    "message": f"Deleted reminder with index {index}",
  }


def update_user_name(name: str, tool_context: ToolContext) -> dict[str, str]:
  """
  Update the user's name.

  :param name: The new name for the user.
  :type name: str
  :param tool_context: Context needed to access the session state
  :type tool_context: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str]
  """
  console.print(f"[tool][TOOL][/] Updating the user name to {name}")

  old_name = tool_context.state.get("user_name", "")
  tool_context.state["user_name"] = name

  return {
    "action": "update_user_name",
    "old_name": old_name,
    "new_name": name,
    "message": f"Updated user name from {old_name} to {name}",
  }
