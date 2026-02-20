from collections.abc import AsyncGenerator
from contextlib import asynccontextmanager
from pathlib import Path
from typing import TYPE_CHECKING

from fastapi import FastAPI, HTTPException, Request, status
from fastapi.exceptions import RequestValidationError
from fastapi.responses import HTMLResponse, JSONResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from starlette.exceptions import HTTPException as StarletteHTTPException

import src.models  # noqa: F401  # pyright: ignore[reportUnusedImport]
from src.db import create_tables
from src.dto.post import PostCreateSchema, PostPatchSchema, PostResponseSchema
from src.dto.user import UserCreateSchema, UserResponseSchema
from src.repos.post import (
    PostsRepoDep,  # noqa: TC001 — required at runtime for FastAPI DI
)
from src.repos.user import (
    UsersRepoDep,  # noqa: TC001 — required at runtime for FastAPI DI
)

if TYPE_CHECKING:
    from src.models.post import Post
    from src.models.user import User

file_dir = Path(__file__).parent.parent

templates = Jinja2Templates(directory=file_dir / "templates")
static_files = StaticFiles(directory=file_dir / "static")
media_files = StaticFiles(directory=file_dir / "media")


@asynccontextmanager
async def lifespan(_app: FastAPI) -> AsyncGenerator[None]:
    # startup
    create_tables()
    yield
    # shutdown


app = FastAPI(lifespan=lifespan)
app.mount("/static", static_files, name="static")
app.mount("/media", media_files, name="media")


# ---------[ Browser routes ]-------


@app.get("/", include_in_schema=False, name="root")
@app.get("/home", include_in_schema=False, name="home")
def home(request: Request, posts_repo: PostsRepoDep) -> HTMLResponse:
    posts = posts_repo.get_all()
    return templates.TemplateResponse(
        request, "home.html.j2", {"title": "Home", "posts": posts}
    )


@app.get("/posts/{post_id}", include_in_schema=False, name="post")
def show_post(request: Request, post_id: int, posts_repo: PostsRepoDep) -> HTMLResponse:
    post = posts_repo.get_by_id(post_id)

    if post:
        return templates.TemplateResponse(
            request, "post.html.j2", {"title": post.title[:50], "post": post}
        )

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Post not found")


@app.get("/users/{user_id}/posts", include_in_schema=False, name="user_posts")
def show_user_posts(
    request: Request, user_id: int, users_repo: UsersRepoDep, posts_repo: PostsRepoDep
) -> HTMLResponse:
    user = users_repo.get_by_id(user_id)

    if user:
        posts = posts_repo.get_by_user_id(user_id)
        return templates.TemplateResponse(
            request,
            "user_posts.html.j2",
            {"title": user.full_name, "user": user, "posts": posts},
        )

    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
    )


# ---------[ API routes ]-------

# Users end-points


@app.post(
    "/api/users", status_code=status.HTTP_201_CREATED, response_model=UserResponseSchema
)
def create_user(new_user: UserCreateSchema, users_repo: UsersRepoDep) -> User:
    return users_repo.create(new_user)


@app.get("/api/users", response_model=list[UserResponseSchema])
def get_users(users_repo: UsersRepoDep) -> list[User]:
    return users_repo.get_all()


@app.get("/api/users/{user_id}", response_model=UserResponseSchema)
def get_user(user_id: int, users_repo: UsersRepoDep) -> User:
    user = users_repo.get_by_id(user_id)

    if not user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Resource not found."
        )

    return user


@app.get("/api/users/{user_id}/posts", response_model=list[PostResponseSchema])
def get_users_posts(
    user_id: int, users_repo: UsersRepoDep, posts_repo: PostsRepoDep
) -> list[Post]:
    user = users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return posts_repo.get_by_user_id(user_id)


# Posts end-points


@app.post(
    "/api/posts", status_code=status.HTTP_201_CREATED, response_model=PostResponseSchema
)
def create_post(new_post: PostCreateSchema, posts_repo: PostsRepoDep) -> Post:
    return posts_repo.create(new_post)


@app.get("/api/posts", response_model=list[PostResponseSchema])
def get_posts(posts_repo: PostsRepoDep) -> list[Post]:
    return posts_repo.get_all()


@app.get("/api/posts/{post_id}", response_model=PostResponseSchema)
def get_post(post_id: int, posts_repo: PostsRepoDep) -> Post:
    post = posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return post


@app.put("/api/posts/{post_id}", response_model=PostResponseSchema)
def update_post(
    post_id: int, post_data: PostCreateSchema, posts_repo: PostsRepoDep
) -> Post:
    post = posts_repo.get_by_id(post_id)

    if post:
        post.title = post_data.title
        post.content = post_data.content
        return posts_repo.update(post)

    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
    )


@app.patch("/api/posts/{post_id}", response_model=PostResponseSchema)
def patch_post(
    post_id: int, post_data: PostPatchSchema, posts_repo: PostsRepoDep
) -> Post:
    post = posts_repo.get_by_id(post_id)

    if post:
        for field in post_data.model_fields_set:
            setattr(post, field, getattr(post_data, field))
        return posts_repo.update(post)

    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
    )


@app.delete("/api/posts/{post_id}")
def delete_post(post_id: int, posts_repo: PostsRepoDep) -> None:
    post = posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    posts_repo.delete(post)


# --------------[ Exception handlers ]-----------


@app.exception_handler(StarletteHTTPException)
def general_http_exception_handler(
    request: Request, exception: StarletteHTTPException
) -> HTMLResponse | JSONResponse:
    message = exception.detail if exception.detail else "An unknown error has occurred."

    if request.url.path.startswith("/api"):
        return JSONResponse(
            status_code=exception.status_code, content={"detail": message}
        )

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
def request_validation_handler(
    request: Request, exception: RequestValidationError
) -> HTMLResponse | JSONResponse:
    if request.url.path.startswith("/api"):
        return JSONResponse(
            status_code=status.HTTP_422_UNPROCESSABLE_CONTENT,
            content={"detail": exception.errors()},
        )

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
