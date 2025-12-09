from typing import TypeAlias

import trio

ResultType: TypeAlias = dict[str, str]


# this is a coroutine function (returns a coroutine object when invoked)
async def computation(
  uid: int,
  delay: float,
  *,
  does_throw: bool = False,
) -> dict[str, str]:
  print(f"Working on computation {uid} for {delay} seconds...")

  # await can only be using in async context
  await trio.sleep(delay)

  if does_throw:
    err_msg = f"Something bad happened with computation {id}"
    raise RuntimeError(err_msg)
  print(f"Computation {id} finished.")

  # return some data
  return {"data": f"some data from {id}"}
