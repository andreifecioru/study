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

    async def get_all(self) -> list[User]:
        result = await self.db.execute(select(User))
        return list(result.scalars().all())

    async def get_by_id(self, user_id: int) -> User | None:
        return await self.db.get(User, user_id)

    async def get_by_username(self, username: str) -> User | None:
        result = await self.db.execute(select(User).where(User.username == username))
        return result.scalars().first()

    async def get_by_email(self, email: str) -> User | None:
        result = await self.db.execute(select(User).where(User.email == email))
        return result.scalars().first()

    async def create(self, new_user: UserCreateSchema) -> User:
        saved_user = User(
            username=new_user.username,
            first_name=new_user.first_name,
            last_name=new_user.last_name,
            email=new_user.email,
        )

        self.db.add(saved_user)
        await self.db.commit()
        await self.db.refresh(saved_user)

        return saved_user

    async def update(self, user: User) -> User:
        await self.db.commit()
        await self.db.refresh(user)

        return user

    async def delete(self, user: User) -> None:
        await self.db.delete(user)
        await self.db.commit()


UsersRepoDep = Annotated[UsersRepository, Depends(UsersRepository)]
