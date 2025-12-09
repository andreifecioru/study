import random
from dataclasses import dataclass
from typing import TypeVar


@dataclass
class User:
  name: str
  age: int | None = None


# generic methods
T = TypeVar("T")


def random_choice(items: list[T]) -> T:
  return random.choice(items)


# there's a newer syntax available that does not require TypeVar (since v3.12)
def random_choice_new[U](items: list[U]) -> U:
  return random.choice(items)


def main() -> None:
  users = [
    User("John", 22),
    User("Bob", 44),
  ]
  print(random_choice(users))

  names = [user.name for user in users]
  print(random_choice_new(names))


if __name__ == "__main__":
  main()
