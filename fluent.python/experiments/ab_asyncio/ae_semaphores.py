import asyncio
import random

semaphore = asyncio.Semaphore(2)

shared_resuource = [0]


async def mutate_resource(uid: int) -> None:
  delay = random.uniform(1.0, 10.0)
  print(f"Task {uid} wants to acquire the lock (delay {delay:.2f}s)")

  async with semaphore:  # grab a semaphore and enter critical section
    print(
      f"Task {uid} acquired a semaphore (value: {shared_resuource}). Remaining permits: {semaphore._value}",  # noqa: SLF001
    )
    await asyncio.sleep(delay)
    shared_resuource[0] += 1
    print(f"Task {uid} releases the semaphore (value: {shared_resuource}).")


async def program_01() -> None:
  tasks = [mutate_resource(i) for i in range(5)]
  await asyncio.gather(*tasks)


async def main() -> None:
  await program_01()


if __name__ == "__main__":
  asyncio.run(main())
