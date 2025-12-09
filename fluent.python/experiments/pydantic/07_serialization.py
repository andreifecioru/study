from typing import Annotated, cast
from pydantic import (
  BaseModel,
  Field,
  UUID4,
  SecretStr,
  ConfigDict
)
from uuid import uuid4


class User(BaseModel):
  # model configuration
  model_config = ConfigDict(
    populate_by_name=True, # enables field-aliases (if data from external sources uses different field names)
    strict=True, # disable field coertion (no more str -> int automatic conversions)
    extra='allow', # accept fields that are not defined in the model (be default they are ignored)
    validate_assignment=True, # applied validation logic upon field assignment (be default validation is only applied at construction time)
    frozen=True, # create immutable models (fields cannot be re-assigned after creation)
  )
  
  uid: UUID4 = Field(alias='id', default_factory=uuid4) # incoming data can use both uid and id
  username: Annotated[str, Field(min_length=1)]
  password: SecretStr


class Comment(BaseModel):
  user: User
  text: Annotated[str, Field(min_length=1)]


class BlogPost(BaseModel):
  title: str
  content: str
  tags: list[str] = Field(default_factory=list)
  comments: list[Comment] = Field(default_factory=list)


def main():
  user = User(
    username='john_doe',
    password=cast(SecretStr, '1121'),
    )
  comment = Comment(
    user=user,
    text='This is a cool blog post!',
  )
  blog_post = BlogPost(
    title='Cool blog post',
    content='This is some cool content',
    tags=['cool'],
    comments=[comment]
  )
  
  json_payload = blog_post.model_dump_json(
    indent=2,
    by_alias=True, # use alias filed names in output
    # exclude fields from serialized output (the excluded field is in a nested object)
    # exclude={'comments': {'__all__': {'user': {'password'}}}}
    
    # we can also use "include" of we want to select a few fields to be included in the output
    include={'title', 'content'}
  )
  
  print(json_payload)
  
  # we can load data from a JSON string
  blog_post_2 = BlogPost.model_validate_json(json_payload)
  print(blog_post_2.model_dump_json(indent=2))


if __name__ == '__main__':
  main()