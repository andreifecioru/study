from typing import Literal, Annotated
from datetime import datetime, UTC
from pydantic import BaseModel, EmailStr, Field, PositiveInt, SecretStr
from functools import partial
from uuid import UUID, uuid4


class BlogPost(BaseModel):
  uid: UUID = Field(default_factory=uuid4)
  # annotated types
  # NOTE: there are a set of pre-defined type aliases for the most common constraints
  #
  # https://docs.pydantic.dev/latest/api/types/#Pydantic%20Types
  # https://docs.pydantic.dev/latest/api/networks/

  title: Annotated[str, Field(min_length=3, max_length=255)]
  content: str
  view_count: PositiveInt = 0
  
  author: str
  author_email: EmailStr # needs email extra (pip install 'pydantic[email]')
  
  secret_key: SecretStr
  
  # pattern match constraints
  slug: Annotated[str, Field(pattern=r'^[a-z0-9-]+$')]
  
  # for mutable default values you cannot pass []
  # you need to provide a function that produces empty list
  #   (technically pydantic handles [] gracefully, but it's bad practice)
  tags: list[str] = Field(default_factory=list) 
  
  # you cannot just pass in datetime.now() as a default value
  # all instances will share the same default value (the timestamp at the time of class definition)
  created_at: datetime = Field(default_factory=lambda: datetime.now(tz=UTC))
  
  # partial function syntax (instead of lamda)
  updated_at: datetime = Field(default_factory=partial(datetime.now, tz=UTC))
  
  # literal types (good for enums)
  status: Literal['draft', 'published', 'archived'] = 'draft'

def main():
  blog_post = BlogPost(
    title='Sample blog-post',
    content='Sample content',
    author='John Doe',
    author_email='john.doe@email.com',
    secret_key=SecretStr('my-secret'), # technically we could have passed 'my-secret' directly, but the type checker complains
    tags=['sample', 'test'],
    slug='sample-blog-post',
  )
  print(f'Blog post (JSON): {blog_post.model_dump_json(indent=2)}')
  
  # extract the secret
  print(f'Secret key is: {blog_post.secret_key.get_secret_value()}')
  

if __name__ == '__main__':
  main()