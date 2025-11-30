import asyncio

async def task_1(event: asyncio.Event) -> None:
    print('Task 1 is waiting for the event')
    await event.wait()
    print('Task 1 received the signal to start working...')
    await asyncio.sleep(1.0)
    print('Task-1 finished working')


async def task_2(event: asyncio.Event) -> None:
    await asyncio.sleep(2.0)
    print('Task-2 is signaling Task-1 to work')
    event.set()


async def main() -> None:
    event = asyncio.Event()
    await asyncio.gather(*[
        task_1(event),
        task_2(event)
    ])


if __name__ == '__main__':
    asyncio.run(main())
