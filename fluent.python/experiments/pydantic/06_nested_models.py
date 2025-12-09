from typing import Annotated
from pydantic import (
  BaseModel,
  Field,
  UUID4
)
from uuid import uuid4


class User(BaseModel):
  uid: UUID4 = Field(default_factory=uuid4)
  username: Annotated[str, Field(min_length=1)]


class Comment(BaseModel):
  user: User
  text: Annotated[str, Field(min_length=1)]


class BlogPost(BaseModel):
  title: str
  content: str
  tags: list[str] = Field(default_factory=list)
  comments: list[Comment] = Field(default_factory=list)


def main():
  user = User(username='john_doe')
  comment = Comment(user=user, text='This is a cool blog post!')
  blog_post = BlogPost(
    title='Cool blog post',
    content='This is some cool content',
    tags=['cool'],
    comments=[comment]
  )
  print(blog_post.model_dump_json(indent=2))


if __name__ == '__main__':
  main()