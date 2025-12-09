import asyncio
import random

lock = asyncio.Lock()

shared_resource = [0]


async def mutate_resource(uid: int) -> None:
  delay = random.uniform(1.0, 10.0)
  print(f"Task {uid} wants to acquire the lock (delay {delay:.2f}s)")

  async with lock:  # grab the lock and enter critical section
    print(f"Task {uid} acquired the lock (value: {shared_resource})")
    await asyncio.sleep(delay)
    shared_resource[0] += 1
    print(f"Task {uid} releases the lock (value: {shared_resource})")


async def program_01() -> None:
  tasks = [mutate_resource(i) for i in range(5)]
  await asyncio.gather(*tasks)


async def main() -> None:
  await program_01()


if __name__ == "__main__":
  asyncio.run(main())
