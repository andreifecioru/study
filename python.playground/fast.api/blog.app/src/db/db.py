from typing import TYPE_CHECKING, Annotated, Any

from fastapi import Depends
from sqlalchemy import event
from sqlalchemy.ext.asyncio import AsyncSession, async_sessionmaker, create_async_engine
from sqlalchemy.orm import DeclarativeBase

if TYPE_CHECKING:
    from collections.abc import AsyncGenerator

DB_URL = "sqlite+aiosqlite:///./blog_app.db"

db_engine = create_async_engine(DB_URL, connect_args={"check_same_thread": False})


# SQLite does not enforce foreign key constraints by default — they must be enabled
# per connection via PRAGMA. We attach this listener to sync_engine (the underlying
# synchronous engine) because SQLAlchemy does not support pool-level events directly
# on AsyncEngine; the async engine delegates connection management to its sync counterpart.
@event.listens_for(db_engine.sync_engine, "connect")
def enable_fk(connection: Any, _: Any) -> None:  # noqa: ANN401
    cursor = connection.cursor()
    cursor.execute("PRAGMA foreign_keys = ON")
    cursor.close()


AsyncSessionFactory = async_sessionmaker(
    bind=db_engine,
    class_=AsyncSession,
    expire_on_commit=False,
)


class ModelBase(DeclarativeBase):
    pass


async def get_db() -> AsyncGenerator[AsyncSession, None]:
    async with AsyncSessionFactory() as db:
        yield db


async def create_tables() -> None:
    async with db_engine.begin() as conn:
        await conn.run_sync(ModelBase.metadata.create_all)


DbDeps = Annotated[AsyncSession, Depends(get_db)]
