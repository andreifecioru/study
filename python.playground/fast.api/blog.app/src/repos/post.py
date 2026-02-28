from typing import TYPE_CHECKING, Annotated

from fastapi import Depends
from sqlalchemy import select
from sqlalchemy.orm import selectinload

from src.db import DbDeps  # noqa: TC001 — required at runtime for FastAPI DI
from src.models.post import Post

if TYPE_CHECKING:
    from src.dto.post import PostCreateSchema


class PostsRepository:
    def __init__(self, db: DbDeps) -> None:
        self.db = db

    async def get_all(self) -> list[Post]:
        result = await self.db.execute(select(Post).options(selectinload(Post.author)))
        return list(result.scalars().all())

    async def get_by_id(self, post_id: int) -> Post | None:
        return await self.db.get(Post, post_id, options=[selectinload(Post.author)])

    async def get_by_user_id(self, user_id: int) -> list[Post]:
        result = await self.db.execute(
            select(Post)
            .options(selectinload(Post.author))
            .where(Post.user_id == user_id)
        )
        return list(result.scalars().all())

    async def create(self, new_post: PostCreateSchema) -> Post:
        saved_post = Post(
            title=new_post.title,
            user_id=new_post.user_id,
            content=new_post.content,
        )

        self.db.add(saved_post)
        await self.db.commit()
        # refresh() reloads scalar columns but not relationships; attribute_names forces
        # the author relationship to be loaded before the session closes
        await self.db.refresh(saved_post, attribute_names=["author"])

        return saved_post

    async def update(self, post: Post) -> Post:
        await self.db.commit()
        await self.db.refresh(post, attribute_names=["author"])
        return post

    async def delete(self, post: Post) -> None:
        await self.db.delete(post)
        await self.db.commit()


PostsRepoDep = Annotated[PostsRepository, Depends(PostsRepository)]
