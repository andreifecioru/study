from collections.abc import Awaitable
from typing import TypeVar

import anyio
from anyio.abc import ObjectSendStream

from .utils import ResultType, computation

T = TypeVar("T")


async def task_wrapper(channel: ObjectSendStream[T], awaitable: Awaitable[T]) -> None:
  result = await awaitable
  await channel.send(result)


async def main() -> None:
  # when starting tasks in a TaskGroup, you don't have handles to the individual tasks to wait on the results
  # we need to communication scafolding in order to access the results.
  results: list[ResultType] = []
  send_channel, recv_channel = anyio.create_memory_object_stream[ResultType](
    max_buffer_size=1,
  )

  async with anyio.create_task_group() as tg:
    tg.start_soon(task_wrapper, send_channel, computation(1, 2.0))
    tg.start_soon(task_wrapper, send_channel, computation(2, 1.0))
    tg.start_soon(task_wrapper, send_channel, computation(3, 4.0))

    for _ in range(3):
      results.append(await recv_channel.receive())

  for result in results:
    print(result)


if __name__ == "__main__":
  anyio.run(main)
