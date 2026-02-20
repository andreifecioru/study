from collections.abc import Generator
from typing import Annotated, Any

from fastapi import Depends
from sqlalchemy import create_engine, event
from sqlalchemy.orm import DeclarativeBase, Session, sessionmaker

DB_URL = "sqlite:///./blog_app.db"

db_engine = create_engine(DB_URL, connect_args={"check_same_thread": False})


@event.listens_for(db_engine, "connect")
def enable_fk(connection: Any, _: Any) -> None:  # noqa: ANN401 — SQLite disables FK constraints by default; must opt in per connection
    cursor = connection.cursor()
    cursor.execute("PRAGMA foreign_keys = ON")
    cursor.close()


SessionFactory = sessionmaker(autocommit=False, autoflush=False, bind=db_engine)


class ModelBase(DeclarativeBase):
    pass


def get_db() -> Generator[Session]:
    with SessionFactory() as db:
        yield db


def create_tables() -> None:
    ModelBase.metadata.create_all(bind=db_engine)


DbDeps = Annotated[Session, Depends(get_db)]
