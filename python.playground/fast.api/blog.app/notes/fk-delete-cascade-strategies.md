# Deleting Entities with FK-Dependent Children (One-to-Many)

When you delete a parent row that has child rows referencing it via a foreign key, the
database will reject the delete by default (integrity constraint violation). You have three
meaningful strategies for handling this, and the right choice depends on whether the
children have independent value or are owned by the parent.

## The Three Options

### 1. Cascade Delete — children die with the parent
Children are automatically deleted when the parent is deleted.

```python
# Post model — child side: tell the DB to cascade
user_id: Mapped[int] = mapped_column(ForeignKey("users.id"), ondelete="CASCADE")

# User model — parent side: tell SQLAlchemy not to load children before deleting
posts: Mapped[list[Post]] = relationship(back_populates="author", passive_deletes=True)
```

**Tradeoff:** Simple and clean, but destructive and irreversible. Only correct when children
have no meaning without the parent.

---

### 2. Restrict — block the delete if children exist
Return an error (e.g. HTTP 409) if the parent has children. Force the caller to clean up first.

```python
# No cascade. Catch the IntegrityError in the repo/route and raise a 409.
```

**Tradeoff:** Safest in terms of data preservation. Requires the caller to manage ordering.
Good when children have independent value (e.g. posts that should outlive a user account).

---

### 3. Soft Delete — mark as deleted, keep the data
Add an `is_deleted: bool` flag. Never issue a real `DELETE`.

```python
user.is_deleted = True
db.commit()
```

**Tradeoff:** Enables undo, audit trails, and historical queries. But every query must filter
`WHERE is_deleted = false`, which is easy to forget and hard to enforce consistently.

---

## Why DB-Level Cascade Wins (When Cascade Is the Right Choice)

The ORM alternative — `cascade="all, delete-orphan"` on the relationship — does the same
thing, but through Python: it loads all child rows into memory, then deletes them one by one,
then deletes the parent. This means N+1 DELETE statements and unnecessary memory use.

DB-level `ondelete="CASCADE"` is a single SQL statement. The database handles it atomically,
with no Python round-trips. Combined with `passive_deletes=True` on the relationship (which
tells SQLAlchemy "don't pre-load children, trust the DB"), you get:

- One DELETE query instead of N+1
- No objects loaded into memory
- Atomicity guaranteed at the DB level
- Consistent behaviour regardless of how the delete is triggered (ORM, raw SQL, migration)

The DB is the right layer for referential integrity. Use it.
