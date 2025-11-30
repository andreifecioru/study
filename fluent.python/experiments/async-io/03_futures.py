import asyncio
from typing import TypeVar

T = TypeVar("T")


async def complete_future(future: asyncio.Future[T], value: T) -> None:
    await asyncio.sleep(2.0)
    print(f'Setting the future value to {value}')
    future.set_result(value)


async def program_01():
    # create a future object
    future = asyncio.get_running_loop().create_future()

    # we create a bg task that will eventually complete the future by seting a result on it
    asyncio.create_task(complete_future(future, 42))

    # await on the future (not the task - that may or may not be complete)
    await future
    print(f'Future result: {future.result()}')


async def main() -> None:
    await program_01()


if __name__ == '__main__':
    asyncio.run(main())
