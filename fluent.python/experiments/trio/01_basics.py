import trio
from utils import computation

async def main() -> None:
    await computation(1, 2.0)

if __name__ == '__main__':
    trio.run(main)
