from collections.abc import Callable, Generator
from functools import wraps
from inspect import getgeneratorstate
from time import sleep
from typing import Any

type AnyFunction = Callable[..., Any]


def decrement(num: int) -> int:
  print(f"Processing: {num}")
  sleep(1)
  return num - 1


def coroutine(func: AnyFunction) -> AnyFunction:
  @wraps(func)
  def primed(*args: list[Any], **kwargs: dict[str, Any]) -> Generator[None]:
    gen = func(*args, **kwargs)
    next(gen)
    return gen

  return primed


# co-routines must be primed before use;
# capture this in a decorator
@coroutine
def processor(func: AnyFunction) -> Generator[None]:
  in_progress = True
  result = None

  while in_progress:
    data = yield result
    result = func(data)


if __name__ == "__main__":
  # create the co-coutine
  proc = processor(decrement)

  # is the co-routine primed?
  print(f"[2]: {getgeneratorstate(proc)}")

  x = 5
  while x > 0:
    x = proc.send(x)

  # optionally, we can explicitly close the co-routine
  proc.close()
