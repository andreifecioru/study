import time

from rich.console import Console
from rich.markdown import Markdown
from rich.progress import track
from rich.table import Table
from rich.text import Text
from rich.theme import Theme
from rich.traceback import install

# trace-backs are now printed with a nice format
install()

console = Console()

DATA = {
  "a": 1,
  "b": 2,
  "c": 3,
}


def console_demo() -> None:
  console.print("==========[ CONSOLE DEMO ]===========")
  console.print(f"Data: {DATA}")
  console.print("Sample text", style="bold italic blue")
  console.print("Sample text", style="bold italic white on red")

  console.print("[bold]This is some [red]sample[/] text.[/]")

  # emoji-markup support
  console.print(":thumbs_up: File downloaded successfuly.")


def text_demo() -> None:
  console.print("\n==========[ TEXT DEMO ]===========")
  text = Text("Hello, world!")
  text.stylize("bold yellow", 0, 6)  # pass in start/stop index
  console.print(text)


def theme_demo() -> None:
  console.print("\n==========[ THEME DEMO ]===========")
  my_theme = Theme(
    {
      "error": "red bold",
      "success": "green",
    }
  )
  themed_console = Console(theme=my_theme)
  themed_console.print("This is an error message", style="error")
  themed_console.print("This is an [error]error[/] and this is [success]success[/].")


def logging_demo() -> None:
  console.print("\n==========[ LOGGING DEMO ]===========")
  log_console = Console(record=True)  # enable console recording

  def add(a: int, b: int) -> int:
    # log local variables
    log_console.log(f"Adding {a} and {b}", log_locals=True)
    return a + b

  add(1, 2)
  # add(1, "2") # this will throw and the traceback will look very nice

  try:
    add(1, 3)
    add(1, "4")
  except Exception:  # noqa: BLE001
    log_console.print_exception()

  # save the whole thing in an html file
  log_console.save_html("logging-demo.html")


def table_demo() -> None:
  console.print("\n==========[ TABLE DEMO ]===========")
  sales = [
    (1, "Laptop", 33, "$50,000"),
    (2, "TV", 123, "$180,000"),
    (3, "Smart Watch", 87, "$40,000"),
  ]

  table = Table(title="Quarterly sales")

  table.add_column("Index", style="white bold")
  table.add_column("Product", style="grey74")
  table.add_column("Items sold", style="grey74")
  table.add_column("Revenue", style="green bold", justify="right")

  for sale in sales:
    table.add_row(*[str(field) for field in sale])

  console.print(table)


def markdown_demo() -> None:
  markdown_text = """
# This is a header

Rich is able to render *a lot* of markdown styles:

1. This is a list item
2. This is a 2nd list item
  """
  md = Markdown(markdown_text)
  console.print(md)


def progressbar_demo() -> None:
  tasks = [f"Task({idx})" for idx in range(10)]
  for task in track(tasks, description="Processing..."):
    print(f"Working on: {task}")
    time.sleep(0.5)


def main() -> None:
  console_demo()
  text_demo()
  theme_demo()
  logging_demo()
  table_demo()
  markdown_demo()
  progressbar_demo()


if __name__ == "__main__":
  main()
