from scripts.data import posts, users
from src.db.db import SessionFactory, create_tables
from src.dto.post import PostCreateSchema
from src.dto.user import UserCreateSchema
from src.repos.post import PostsRepository
from src.repos.user import UsersRepository


def create_users(users_repo: UsersRepository) -> None:
  for user in users:
    new_user = UserCreateSchema(
      username=user["username"],
      first_name=user["first_name"],
      last_name=user["last_name"],
      email=user["email"],
    )
    users_repo.create(new_user)


def create_posts(users_repo: UsersRepository, posts_repo: PostsRepository) -> None:
  for post in posts:
    user = users_repo.get_by_username(post["author"])
    if user:
      new_post = PostCreateSchema(
        title=post["title"], content=post["content"], user_id=user.id
      )
      posts_repo.create(new_post)
    else:
      msg = f"Unable to find user: {post['author']}"
      raise ValueError(msg)


if __name__ == "__main__":
  with SessionFactory() as session:
    create_tables()

    users_repo = UsersRepository(db=session)
    posts_repo = PostsRepository(db=session)

    create_users(users_repo)
    create_posts(users_repo, posts_repo)
