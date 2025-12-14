from google.adk.events import Event
from google.adk.runners import Runner
from google.genai import types
from rich.console import Console
from rich.syntax import Syntax
from rich.theme import Theme

app_theme = Theme(
  {
    "agent": "blue bold",
    "debug": "grey74",
  }
)
console = Console(theme=app_theme)


def log_event(event: Event) -> None:
  console.print(
    f"[agent][AGENT][/] Processing event {event.id}. Author: {event.author}"
  )
  if event.content and event.content.parts:
    for part in event.content.parts:
      if (
        hasattr(part, "executable_code")
        and part.executable_code
        and part.executable_code.code
      ):
        syntax = Syntax(part.executable_code.code, "python")
        console.print(syntax)
      elif hasattr(part, "code_execution_result") and part.code_execution_result:
        console.print(
          f"[AGENT] Code execution result: {part.code_execution_result.outcome}",
          style="debug",
        )
        console.print(
          f"[AGENT] Code execution output: {part.code_execution_result.output}",
          style="debug",
        )
      elif hasattr(part, "function_call") and part.function_call:
        console.print(f"[AGENT] Fn call: {part.function_call}", style="debug")
      elif hasattr(part, "function_response") and part.function_response:
        console.print(
          f"[AGENT] Fn call response: {part.function_response.response}", style="debug"
        )
      elif hasattr(part, "text") and part.text:
        console.print(f"[AGENT] Text: {part.text.strip()}")


def process_agent_response(event: Event) -> str | None:
  log_event(event)

  final_response = None
  if (
    event.is_final_response()
    and event.content
    and event.content.parts
    and hasattr(event.content.parts[0], "text")
    and event.content.parts[0].text
  ):
    final_response = event.content.parts[0].text.strip()
    console.print(f"[agent][AGENT][/] Agent repsonse: {final_response}")
  else:
    console.print("[agent][AGENT][/] Agent repsonse: no text in final agent response")

  return final_response


async def call_agent(runner: Runner, user_id: str, session_id: str, query: str) -> str | None:
  content = types.Content(role="user", parts=[types.Part(text=query)])
  final_response_text = None

  console.print(f"[agent][AGENT][/] Running query: {query}")

  # TODO: Display state before processing

  try:
    async for event in runner.run_async(
      user_id=user_id, session_id=session_id, new_message=content
    ):
      final_response_text = process_agent_response(event)
  except Exception:
    console.print_exception()

  # TODO: Display state after processing

  return final_response_text
