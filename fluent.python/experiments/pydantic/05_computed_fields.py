from typing import Annotated

from pydantic import (
  BaseModel,
  Field,
  computed_field
)

class User(BaseModel):
  first_name: Annotated[str, Field(min_length=3, max_length=20)]
  last_name: Annotated[str, Field(min_length=3, max_length=20)]
  
  @computed_field
  @property
  def full_name(self) -> str:
    return f'{self.first_name} {self.last_name}'


def main():
  user = User(
    first_name='John',
    last_name='Doe'
  )
  
  # the computed fields will be included in the JSON representation
  print(f'User: {user.model_dump_json(indent=2)}')


if __name__ == '__main__':
  main()