import asyncio
from utils import computation


async def program_001() -> None:
    try:
        result = await computation(1, 2.0, True)
        print(f'result: {result}')
    except RuntimeError as e:
        print(f'Failure: {e}')

async def program_002() -> None:
    c1 = computation(1, 1.0)
    c2 = computation(2, 2.0)

    r1 = await c1
    print(f'{r1}\n')

    r2 = await c2
    print(r2)


async def main():
    await program_002()


if __name__ == '__main__':
    asyncio.run(main())