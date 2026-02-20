from typing import TYPE_CHECKING, Annotated

from fastapi import Depends
from sqlalchemy import select

from src.db import DbDeps  # noqa: TC001 — required at runtime for FastAPI DI
from src.models.post import Post

if TYPE_CHECKING:
    from src.dto.post import PostCreateSchema


class PostsRepository:
    def __init__(self, db: DbDeps) -> None:
        self.db = db

    def get_all(self) -> list[Post]:
        return list(self.db.scalars(select(Post)).all())

    def get_by_id(self, post_id: int) -> Post | None:
        return self.db.get(Post, post_id)

    def get_by_user_id(self, user_id: int) -> list[Post]:
        return list(self.db.scalars(select(Post).where(Post.user_id == user_id)).all())

    def create(self, new_post: PostCreateSchema) -> Post:
        saved_post = Post(
            title=new_post.title,
            user_id=new_post.user_id,
            content=new_post.content,
        )

        self.db.add(saved_post)
        self.db.commit()
        self.db.refresh(saved_post)

        return saved_post

    def update(self, post: Post) -> Post:
        self.db.commit()
        self.db.refresh(post)
        return post

    def delete(self, post: Post) -> None:
        self.db.delete(post)
        self.db.commit()


PostsRepoDep = Annotated[PostsRepository, Depends(PostsRepository)]
