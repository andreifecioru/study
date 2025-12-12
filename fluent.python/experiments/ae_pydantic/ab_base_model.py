from typing import Literal

import pydantic
from pydantic import BaseModel


# it's similar with data-classes but with run-time validation
class User(BaseModel):
  uid: int
  name: str
  age: int
  bio: str | None = None
  gender: Literal["male", "female"]  # literal types (good for enums)
  is_active: bool = True


def main() -> None:
  user = User(
    uid=1,
    name="John",
    age=23,
    gender="male",
  )

  # user.age = "twenty" # this is flagged by the type checker

  # NOTE: by default, the model is not re-validated on field assignment
  print(f"User: {user}")

  # convert to dictionary
  user_dict = user.model_dump()
  print(f"User dict: {user_dict}")

  # we can re-construct the model from the dict representation
  # same as: User.model_validate(user_dict)
  reconstructed_user = User(**user_dict)
  print(f"Reconstructed user: {reconstructed_user}")

  # convert to json
  user_json = user.model_dump_json(indent=2)
  print(f"User JSON: {user_json}")


if __name__ == "__main__":
  print(f"Pydantic version: {pydantic.__version__}")
  main()
