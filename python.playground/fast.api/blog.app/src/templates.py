from pathlib import Path

from fastapi.templating import Jinja2Templates

file_dir = Path(__file__).parent.parent

templates = Jinja2Templates(directory=file_dir / "templates")
