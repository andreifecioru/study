# SQLAlchemy Async DB Access and Eager Loading with selectinload

SQLAlchemy's async session cannot perform I/O implicitly. Lazy loading — where
accessing `post.author` triggers a hidden SQL query — works in sync code because
the ORM can block the thread. In async code there is no event loop running at
attribute-access time, so SQLAlchemy raises `MissingGreenlet` instead. The fix
is to declare *at query time* which relationships you need, so all I/O happens
inside the `await`, not later when you read an attribute.

```python
from sqlalchemy import select
from sqlalchemy.orm import selectinload

# SELECT all posts + their authors (two queries: one for posts, one IN-query for authors)
result = await self.db.execute(select(Post).options(selectinload(Post.author)))
posts = list(result.scalars().all())

# Fetch by PK + eagerly load the relationship
post = await self.db.get(Post, post_id, options=[selectinload(Post.author)])

# After create/commit, refresh reloads scalar columns but NOT relationships by default;
# attribute_names forces the relationship to be fetched before the session closes
self.db.add(new_post)
await self.db.commit()
await self.db.refresh(new_post, attribute_names=["author"])
```

**Tradeoffs and caveats**

- `selectinload` issues two queries: one for the primary rows, then one `IN (...)` query
  for the related rows. This is usually preferable to `joinedload` (a JOIN) for
  one-to-many relationships because it avoids duplicating the parent row per child.
- `lazy="joined"` on the model definition applies a JOIN globally, but it is
  incompatible with async because SQLAlchemy still marks the strategy as
  requiring a greenlet context. Explicit `selectinload` at query time is safer
  and more intentional.
- `session.refresh(obj)` without `attribute_names` only reloads scalar columns —
  relationships stay expired. Pass `attribute_names=["author"]` explicitly when
  you need the relationship populated immediately (e.g. before returning the
  object from a `create` method while the session is still open).
- `session.add()` is the only common session method that is sync (it just registers
  the object in the identity map, no I/O). Everything else — `commit()`, `refresh()`,
  `execute()`, `get()`, `delete()` — is async and must be awaited.
