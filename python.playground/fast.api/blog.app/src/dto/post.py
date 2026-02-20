from datetime import datetime

from pydantic import BaseModel, ConfigDict, Field

from src.dto.user import (
    UserResponseSchema,  # noqa: TC001 — required at runtime by the swagger endpoint
)


class PostBaseSchema(BaseModel):
    title: str = Field(min_length=1, max_length=100)
    content: str = Field(min_length=1)


class PostCreateSchema(PostBaseSchema):
    user_id: int


class PostResponseSchema(PostBaseSchema):
    model_config = ConfigDict(from_attributes=True)

    id: int
    user_id: int
    date_posted: datetime
    author: UserResponseSchema


class PostPatchSchema(BaseModel):
    title: str | None = Field(min_length=1, max_length=100, default=None)
    content: str | None = Field(min_length=1, default=None)
