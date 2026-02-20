from typing import TYPE_CHECKING, Annotated

from fastapi import Depends
from sqlalchemy import select

from src.db import DbDeps  # noqa: TC001 — required at runtime for FastAPI DI
from src.models.user import User

if TYPE_CHECKING:
    from src.dto.user import UserCreateSchema


class UsersRepository:
    def __init__(self, db: DbDeps) -> None:
        self.db = db

    def get_all(self) -> list[User]:
        return list(self.db.scalars(select(User)).all())

    def get_by_id(self, user_id: int) -> User | None:
        return self.db.get(User, user_id)

    def get_by_username(self, username: str) -> User | None:
        return self.db.scalars(select(User).where(User.username == username)).first()

    def create(self, new_user: UserCreateSchema) -> User:
        saved_user = User(
            username=new_user.username,
            first_name=new_user.first_name,
            last_name=new_user.last_name,
            email=new_user.email,
        )

        self.db.add(saved_user)
        self.db.commit()
        self.db.refresh(saved_user)

        return saved_user

    def delete(self, user: User) -> None:
        self.db.delete(user)
        self.db.commit()


UsersRepoDep = Annotated[UsersRepository, Depends(UsersRepository)]
