import anyio
from typing import TypeAlias

ResultType: TypeAlias = dict[str, str]

# this is a coroutine function (returns a coroutine object when invoked)
async def computation(id: int, delay: float, throws: bool = False) -> dict[str, str]:
    print(f'Working on computation {id} for {delay} seconds...')

    # await can only be using in async context
    await anyio.sleep(delay)

    if throws:
        raise RuntimeError(f'Something bad happened with computation {id}')
    print(f'Computation {id} finished.')

    # return some data
    return {'data': f'some data from {id}'}
