import asyncio
from typing import TypeAlias
from utils import computation, ResultType

TaskResult: TypeAlias = asyncio.Task[ResultType]

def on_task_done(task: asyncio.Task[dict[str, str]]) -> None:
    exc = task.exception()
    if exc:
        print(exc)


async def program_01() -> None:
    t1 = asyncio.create_task(computation(1, 1.0))
    t2 = asyncio.create_task(computation(2, 2.0, True))
    t3 = asyncio.create_task(computation(3, 0.5))

    # add task callback
    for task in [t1, t2, t3]:
        task.add_done_callback(on_task_done)

    # the coroutines are run in parallel (not sequentially)
    r1 = await t1
    r2 = await t2 # this task fails and the exception is re-raised
    r3 = await t3

    for result in [r1, r2, r3]:
        print(result)


async def program_02() -> None:
    tasks = [
        asyncio.create_task(computation(1, 1.0)),
        asyncio.create_task(computation(2, 2.0, True)),
        asyncio.create_task(computation(3, 0.5))
    ]

    # run the coroutines in parallel and gather the results
    # if we don't specify the return_exception=True paranm the 2nd computation will raise
    results = await asyncio.gather(*tasks, return_exceptions=True)

    for result in results:
        if isinstance(result, Exception):
            print(f'ERROR: {result}')
        else:
            print(result)

async def program_03() -> None:
    tasks: list[TaskResult] = []

    async with asyncio.TaskGroup() as tg:
        for idx, sleep_time in enumerate([1.0, 2.0, 0.5], start=1):
            task = tg.create_task(computation(idx, sleep_time, throws=(idx == 1)))
            tasks.append(task)

    # when the async context manager above exits, all tasks are done
    # NOTE: only the 3rd task gets to finish, as the 1st task fails after 1 sec, the 2nd one gets cancelled
    for task in tasks:
        print(task.result())


async def main() -> None:
    await program_03()


if __name__ == '__main__':
    asyncio.run(main())
