from typing import TypeAlias

import anyio

ResultType: TypeAlias = dict[str, str]


# this is a coroutine function (returns a coroutine object when invoked)
async def computation(uid: int, delay: float, *, does_thow: bool = False) -> dict[str, str]:
  print(f"Working on computation {id} for {delay} seconds...")

  # await can only be using in async context
  await anyio.sleep(delay)

  if does_thow:
    err_msg = f"Something bad happened with computation {uid}"
    raise RuntimeError(err_msg)
  print(f"Computation {id} finished.")

  # return some data
  return {"data": f"some data from {id}"}
