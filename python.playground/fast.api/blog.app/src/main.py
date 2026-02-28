from collections.abc import AsyncGenerator
from contextlib import asynccontextmanager
from pathlib import Path
from typing import TYPE_CHECKING

from fastapi import FastAPI, HTTPException, Request, status
from fastapi.exception_handlers import (
    http_exception_handler,
    request_validation_exception_handler,
)
from fastapi.exceptions import RequestValidationError
from fastapi.responses import Response, HTMLResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from starlette.exceptions import HTTPException as StarletteHTTPException

import src.models  # noqa: F401  # pyright: ignore[reportUnusedImport]
from src.db import create_tables, db_engine
from src.dto.post import PostCreateSchema, PostPatchSchema, PostResponseSchema
from src.dto.user import UserCreateSchema, UserPatchSchema, UserResponseSchema
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
    await create_tables()
    yield
    # shutdown
    await db_engine.dispose()


app = FastAPI(lifespan=lifespan)
app.mount("/static", static_files, name="static")
app.mount("/media", media_files, name="media")


# ---------[ Browser routes ]-------


@app.get("/", include_in_schema=False, name="root")
@app.get("/home", include_in_schema=False, name="home")
async def home(request: Request, posts_repo: PostsRepoDep) -> HTMLResponse:
    posts = await posts_repo.get_all()
    return templates.TemplateResponse(
        request, "home.html.j2", {"title": "Home", "posts": posts}
    )


@app.get("/posts/{post_id}", include_in_schema=False, name="post")
async def show_post(
    request: Request, post_id: int, posts_repo: PostsRepoDep
) -> HTMLResponse:
    post = await posts_repo.get_by_id(post_id)

    if post:
        return templates.TemplateResponse(
            request, "post.html.j2", {"title": post.title[:50], "post": post}
        )

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Post not found")


@app.get("/users/{user_id}/posts", include_in_schema=False, name="user_posts")
async def show_user_posts(
    request: Request, user_id: int, users_repo: UsersRepoDep, posts_repo: PostsRepoDep
) -> HTMLResponse:
    user = await users_repo.get_by_id(user_id)

    if user:
        posts = await posts_repo.get_by_user_id(user_id)
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
async def create_user(new_user: UserCreateSchema, users_repo: UsersRepoDep) -> User:
    return await users_repo.create(new_user)


@app.get("/api/users", response_model=list[UserResponseSchema])
async def get_users(users_repo: UsersRepoDep) -> list[User]:
    return await users_repo.get_all()


@app.get("/api/users/{user_id}", response_model=UserResponseSchema)
async def get_user(user_id: int, users_repo: UsersRepoDep) -> User:
    user = await users_repo.get_by_id(user_id)

    if not user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Resource not found."
        )

    return user


@app.put("/api/users/{user_id}", response_model=UserResponseSchema)
async def put_user(
    user_id: int, user_data: UserCreateSchema, users_repo: UsersRepoDep
) -> User:
    user = await users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    user_with_username = await users_repo.get_by_username(user_data.username)
    if user_with_username is not None and user_with_username.id != user_id:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="User name already exists",
        )

    user_with_email = await users_repo.get_by_email(user_data.email)
    if user_with_email is not None and user_with_email.id != user_id:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="User email already exists",
        )

    user.username = user_data.username
    user.first_name = user_data.first_name
    user.last_name = user_data.last_name
    user.email = user_data.email

    return await users_repo.update(user)


@app.patch("/api/users/{user_id}", response_model=UserResponseSchema)
async def patch_user(
    user_id: int, patch_data: UserPatchSchema, users_repo: UsersRepoDep
) -> User:
    user = await users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    if patch_data.username is not None and patch_data.username != user.username:
        user_with_username = await users_repo.get_by_username(patch_data.username)
        if user_with_username is not None:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="User name already exists",
            )

    if patch_data.email is not None and patch_data.email != user.email:
        user_with_email = await users_repo.get_by_email(patch_data.email)
        if user_with_email is not None:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="User email already exists",
            )

    for field in patch_data.model_fields_set:
        setattr(user, field, getattr(patch_data, field))
    return await users_repo.update(user)


@app.get("/api/users/{user_id}/posts", response_model=list[PostResponseSchema])
async def get_users_posts(
    user_id: int, users_repo: UsersRepoDep, posts_repo: PostsRepoDep
) -> list[Post]:
    user = await users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return await posts_repo.get_by_user_id(user_id)


# Posts end-points


@app.post(
    "/api/posts", status_code=status.HTTP_201_CREATED, response_model=PostResponseSchema
)
async def create_post(new_post: PostCreateSchema, posts_repo: PostsRepoDep) -> Post:
    return await posts_repo.create(new_post)


@app.get("/api/posts", response_model=list[PostResponseSchema])
async def get_posts(posts_repo: PostsRepoDep) -> list[Post]:
    return await posts_repo.get_all()


@app.get("/api/posts/{post_id}", response_model=PostResponseSchema)
async def get_post(post_id: int, posts_repo: PostsRepoDep) -> Post:
    post = await posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return post


@app.put("/api/posts/{post_id}", response_model=PostResponseSchema)
async def update_post(
    post_id: int, post_data: PostCreateSchema, posts_repo: PostsRepoDep
) -> Post:
    post = await posts_repo.get_by_id(post_id)

    if post:
        post.title = post_data.title
        post.content = post_data.content
        return await posts_repo.update(post)

    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
    )


@app.patch("/api/posts/{post_id}", response_model=PostResponseSchema)
async def patch_post(
    post_id: int, post_data: PostPatchSchema, posts_repo: PostsRepoDep
) -> Post:
    post = await posts_repo.get_by_id(post_id)

    if post:
        for field in post_data.model_fields_set:
            setattr(post, field, getattr(post_data, field))
        return await posts_repo.update(post)

    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
    )


@app.delete("/api/posts/{post_id}")
async def delete_post(post_id: int, posts_repo: PostsRepoDep) -> None:
    post = await posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    await posts_repo.delete(post)


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
