from typing import TYPE_CHECKING

from fastapi import APIRouter, Request, status
from fastapi.exceptions import HTTPException

from src.dto.post import PostResponseSchema
from src.dto.user import UserCreateSchema, UserPatchSchema, UserResponseSchema
from src.repos.post import (
    PostsRepoDep,  # noqa: TC001 — required at runtime for FastAPI DI
)
from src.repos.user import (
    UsersRepoDep,  # noqa: TC001 — required at runtime for FastAPI DI
)
from src.templates import templates

if TYPE_CHECKING:
    from fastapi.responses import HTMLResponse

    from src.models import Post, User

template_router = APIRouter()
api_router = APIRouter(prefix="/api/v1/users", tags=["users"])


# ---------------[ Template routes ]---------------
@template_router.get(
    "/users/{user_id}/posts", include_in_schema=False, name="page:users:posts:list"
)
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


# ---------------[ API routes ]---------------


@api_router.post(
    "",
    status_code=status.HTTP_201_CREATED,
    response_model=UserResponseSchema,
    name="api:users:create",
)
async def create_user(new_user: UserCreateSchema, users_repo: UsersRepoDep) -> User:
    return await users_repo.create(new_user)


@api_router.get("", response_model=list[UserResponseSchema], name="api:users:list")
async def get_users(users_repo: UsersRepoDep) -> list[User]:
    return await users_repo.get_all()


@api_router.get(
    "/{user_id}", response_model=UserResponseSchema, name="api:users:detail"
)
async def get_user(user_id: int, users_repo: UsersRepoDep) -> User:
    user = await users_repo.get_by_id(user_id)

    if not user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Resource not found."
        )

    return user


@api_router.put("/{user_id}", response_model=UserResponseSchema, name="api:users:put")
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


@api_router.patch(
    "/{user_id}", response_model=UserResponseSchema, name="api:users:patch"
)
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


@api_router.get(
    "/{user_id}/posts",
    response_model=list[PostResponseSchema],
    name="api:users:posts:list",
)
async def get_users_posts(
    user_id: int, users_repo: UsersRepoDep, posts_repo: PostsRepoDep
) -> list[Post]:
    user = await users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return await posts_repo.get_by_user_id(user_id)


@api_router.delete("/{user_id}", name="api:users:delete")
async def delete_user(user_id: int, users_repo: UsersRepoDep) -> None:
    user = await users_repo.get_by_id(user_id)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    await users_repo.delete(user)


router = APIRouter()
router.include_router(template_router)
router.include_router(api_router)
