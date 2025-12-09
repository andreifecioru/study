from dataclasses import dataclass
from typing import NewType, TypedDict

# type aliases
RGB = NewType("RGB", tuple[int, int, int])
HSL = NewType("HSL", tuple[int, int, int])


# this may look like a class, but we are defining the 'shape' of User (a type-hinted dict)
# (similar with interfaces in TS)
class User(TypedDict):
  name: str
  age: int | None
  fav_color: RGB | None


# NOTE: we can use data-classes instead (if we want to add methods or default values)
@dataclass
class UserData:
  name: str
  age: int | None = None
  fav_color: RGB | None = None


def create_user(
  name: str,
  age: int | None = None,
  fav_color: RGB | None = None,
) -> User:
  return {"name": name, "age": age, "fav_color": fav_color}


def main() -> None:
  rgb_color = RGB((255, 0, 128))
  user = create_user("John", age=32, fav_color=rgb_color)
  print(user)


if __name__ == "__main__":
  main()
