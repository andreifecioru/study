from __future__ import annotations

from typing import TYPE_CHECKING

from sqlalchemy import String
from sqlalchemy.orm import Mapped, mapped_column, relationship

from src.db import ModelBase

if TYPE_CHECKING:
    from src.models.post import Post


class User(ModelBase):
    __tablename__ = "users"

    id: Mapped[int] = mapped_column(primary_key=True)
    username: Mapped[str] = mapped_column(String(50), unique=True)
    first_name: Mapped[str] = mapped_column(String(30))
    last_name: Mapped[str] = mapped_column(String(30))
    email: Mapped[str] = mapped_column(String(150), unique=True)
    image_file: Mapped[str | None] = mapped_column(String(200), default=None)

    posts: Mapped[list[Post]] = relationship(
        back_populates="author", passive_deletes=True
    )

    @property
    def full_name(self) -> str:
        return f"{self.first_name} {self.last_name}"

    @property
    def image_path(self) -> str:
        if self.image_file:
            return f"/media/profile_pics/{self.image_file}"

        return "/static/profile_pics/default.jpg"
