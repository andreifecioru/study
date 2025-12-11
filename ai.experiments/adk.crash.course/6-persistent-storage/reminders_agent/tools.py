from google.adk.tools.tool_context import ToolContext


def add_reminder(reminder: str, tool_ctx: ToolContext) -> dict[str, str]:
  """
  Add a new reminder to the user's reminder list.

  :param reminder: The new reminder to be added
  :type reminder: str
  :param tool_ctx: Context needed to access the session state
  :type tool_ctx: ToolContext
  :return: A confirmation message
  :rtype: dict[str, str]
  """
  print(f"[TOOL] Adding reminder: {reminder}")

  # Append the new reminder to the current state
  tool_ctx.state.get("reminders", []).append(reminder)

  return {
    "action": "add_reminder",
    "reminder": reminder,
    "message": f"Reminder added: {reminder}"
  }


def list_reminders(tool_ctx: ToolContext) -> dict[str, str | list[str]]:
  """
  List the current reminders.

  :param tool_ctx: Context needed to access the session state
  :type tool_ctx: ToolContext
  :return: Message containing the list of current reminders.
  :rtype: dict[str, str]
  """
  print("[TOOL] Listing existing reminders")

  # Extract the reminders from the current state
  reminders = tool_ctx.state.get("reminders", [])

  return {
    "action": "list_reminders",
    "reminders": reminders,
    "message": f"Record count: {len(reminders)}"
  }

