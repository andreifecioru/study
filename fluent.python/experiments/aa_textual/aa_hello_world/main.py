from collections.abc import Generator
from typing import override

from textual.app import App
from textual.widget import Widget
from textual.widgets import Static


class MainApp(App[None]):
    @override
    def compose(self) -> Generator[Widget]:
        yield Static("Hello, world!")


def main() -> None:
    app = MainApp()
    app.run()


if __name__ == "__main__":
    main()
