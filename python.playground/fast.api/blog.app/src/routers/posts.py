from typing import TYPE_CHECKING

from fastapi import APIRouter, Request, status
from fastapi.exceptions import HTTPException

from src.dto.post import PostCreateSchema, PostPatchSchema, PostResponseSchema
from src.repos.post import (
    PostsRepoDep,  # noqa: TC001 — required at runtime for FastAPI DI
)
from src.templates import templates

if TYPE_CHECKING:
    from fastapi.responses import HTMLResponse

    from src.models import Post

template_router = APIRouter()
api_router = APIRouter(prefix="/api/v1/posts", tags=["posts"])


# ---------------[ Template routes ]---------------


@template_router.get("/", include_in_schema=False, name="page:root")
@template_router.get("/home", include_in_schema=False, name="page:home")
async def home(request: Request, posts_repo: PostsRepoDep) -> HTMLResponse:
    posts = await posts_repo.get_all()
    return templates.TemplateResponse(
        request, "home.html.j2", {"title": "Home", "posts": posts}
    )


@template_router.get(
    "/posts/{post_id}", include_in_schema=False, name="page:posts:detail"
)
async def show_post(
    request: Request, post_id: int, posts_repo: PostsRepoDep
) -> HTMLResponse:
    post = await posts_repo.get_by_id(post_id)

    if post:
        return templates.TemplateResponse(
            request, "post.html.j2", {"title": post.title[:50], "post": post}
        )

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Post not found")


# ---------------[ API routes ]---------------


@api_router.post(
    "",
    status_code=status.HTTP_201_CREATED,
    response_model=PostResponseSchema,
    name="api:posts:create",
)
async def create_post(new_post: PostCreateSchema, posts_repo: PostsRepoDep) -> Post:
    return await posts_repo.create(new_post)


@api_router.get("", response_model=list[PostResponseSchema], name="api:posts:list")
async def get_posts(posts_repo: PostsRepoDep) -> list[Post]:
    return await posts_repo.get_all()


@api_router.get(
    "/{post_id}", response_model=PostResponseSchema, name="api:posts:detail"
)
async def get_post(post_id: int, posts_repo: PostsRepoDep) -> Post:
    post = await posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    return post


@api_router.put("/{post_id}", response_model=PostResponseSchema, name="api:posts:put")
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


@api_router.patch(
    "/{post_id}", response_model=PostResponseSchema, name="api:posts:patch"
)
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


@api_router.delete("/{post_id}", name="api:posts:delete")
async def delete_post(post_id: int, posts_repo: PostsRepoDep) -> None:
    post = await posts_repo.get_by_id(post_id)

    if not post:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Resource not found."
        )

    await posts_repo.delete(post)


router = APIRouter()
router.include_router(template_router)
router.include_router(api_router)
