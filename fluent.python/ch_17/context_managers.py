from collections.abc import Generator
from contextlib import contextmanager
from types import TracebackType


class Person:
  def __init__(self, first_name: str, last_name: str) -> None:
    self.__first_name = first_name
    self.__last_name = last_name


class PatchPerson:
  @staticmethod
  def __full_name(person: Person) -> str:
    return f"{person._Person__first_name} {person._Person__last_name}"  # type: ignore[attr-defined] # noqa: SLF001

  def __enter__(self) -> None:
    Person.full_name = PatchPerson.__full_name  # type: ignore[attr-defined]

  def __exit__(
    self,
    exc_type: type[BaseException] | None,
    exc_value: BaseException | None,
    traceback: TracebackType | None,
  ) -> None:
    del Person.full_name  # type: ignore[attr-defined]


@contextmanager
def patch_person() -> Generator[None]:
  def __full_name(person: Person) -> str:
    return f"{person._Person__first_name} {person._Person__last_name}"  # type: ignore[attr-defined] # noqa: SLF001

  # this part corresponds to the '__enter__' special method
  Person.full_name = __full_name  # type: ignore[attr-defined]
  try:
    yield
  except Exception:
    # allow exception to bubble up (see [1])
    print("Something bad happened")
    raise
  finally:
    # this part corresponds to the '__exit__' special method
    del Person.full_name  # type: ignore[attr-defined]


if __name__ == "__main__":
  andrei = Person("Andrei", "Fecioru")

  with PatchPerson():
    print(f"[IN] Andrei's full name is: {andrei.full_name()}")  # type: ignore[attr-defined]

  with patch_person():
    print(f"[IN] Andrei's full name is: {andrei.full_name()}")  # type: ignore[attr-defined]

  print(f"[OUT] Andrei's full name is: {andrei.full_name()}")  # type: ignore[attr-defined]
