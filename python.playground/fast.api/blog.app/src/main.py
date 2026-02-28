from contextlib import asynccontextmanager
from pathlib import Path
from typing import TYPE_CHECKING

from fastapi import FastAPI, Request, status
from fastapi.exception_handlers import (
    http_exception_handler,
    request_validation_exception_handler,
)
from fastapi.exceptions import RequestValidationError
from fastapi.staticfiles import StaticFiles
from starlette.exceptions import HTTPException as StarletteHTTPException

# registers model classes with ModelBase.metadata so create_tables() sees them at startup
import src.models  # noqa: F401  # pyright: ignore[reportUnusedImport]
from src.db import create_tables, db_engine
from src.routers import posts, users
from src.templates import templates

if TYPE_CHECKING:
    from collections.abc import AsyncGenerator

    from fastapi.responses import Response

file_dir = Path(__file__).parent.parent

static_files = StaticFiles(directory=file_dir / "static")
media_files = StaticFiles(directory=file_dir / "media")


@asynccontextmanager
async def lifespan(_app: FastAPI) -> AsyncGenerator[None]:
    # startup
    await create_tables()
    yield
    # shutdown
    await db_engine.dispose()


app = FastAPI(lifespan=lifespan)
app.mount("/static", static_files, name="static")
app.mount("/media", media_files, name="media")

app.include_router(posts.router)
app.include_router(users.router)


# --------------[ Exception handlers ]-----------


@app.exception_handler(StarletteHTTPException)
async def general_http_exception_handler(
    request: Request, exception: StarletteHTTPException
) -> Response:
    if request.url.path.startswith("/api"):
        return await http_exception_handler(request, exception)

    message = exception.detail if exception.detail else "An unknown error has occurred."
    return templates.TemplateResponse(
        request,
        "error.html.j2",
        {
            "status_code": exception.status_code,
            "title": exception.status_code,
            "message": message,
        },
        status_code=exception.status_code,
    )


@app.exception_handler(RequestValidationError)
async def request_validation_handler(
    request: Request, exception: RequestValidationError
) -> Response:
    if request.url.path.startswith("/api"):
        return await request_validation_exception_handler(request, exception)

    return templates.TemplateResponse(
        request,
        "error.html.j2",
        {
            "status_code": status.HTTP_422_UNPROCESSABLE_CONTENT,
            "title": status.HTTP_422_UNPROCESSABLE_CONTENT,
            "message": "Invalid request. Please try again.",
        },
        status_code=status.HTTP_422_UNPROCESSABLE_CONTENT,
    )
