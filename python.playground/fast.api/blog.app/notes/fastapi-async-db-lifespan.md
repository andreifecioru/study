# FastAPI Async DB Setup and Lifespan Hook

FastAPI applications need two things from the database layer: a connection pool that
persists for the life of the process, and short-lived sessions scoped to individual
requests. The `lifespan` context manager is the right place to manage the pool
(create tables on startup, dispose the engine on shutdown), while a `yield` dependency
manages the per-request session. This keeps resource ownership explicit and avoids
keeping connections open longer than needed.

```python
# db.py — engine, session factory, and request-scoped dependency
DB_URL = "sqlite+aiosqlite:///./blog_app.db"
db_engine = create_async_engine(DB_URL)
AsyncSessionFactory = async_sessionmaker(bind=db_engine, class_=AsyncSession, expire_on_commit=False)

async def get_db() -> AsyncGenerator[AsyncSession, None]:
    async with AsyncSessionFactory() as db:
        yield db                      # session is open only for the duration of the request

DbDeps = Annotated[AsyncSession, Depends(get_db)]  # shorthand for route signatures

# main.py — startup and shutdown via lifespan
@asynccontextmanager
async def lifespan(_app: FastAPI) -> AsyncGenerator[None]:
    await create_tables()             # DDL runs once at startup
    yield
    await db_engine.dispose()         # closes all pooled connections cleanly

app = FastAPI(lifespan=lifespan)
```

**Tradeoffs and caveats**

- `expire_on_commit=False` — by default SQLAlchemy expires all attributes after
  `commit()`, which would trigger a lazy load (and a `MissingGreenlet` error) the
  next time you access a field. Setting this to `False` keeps the in-memory state
  as-is after commit, which is safe for the short-lived request session pattern.
- `db_engine.dispose()` on shutdown is a best-effort clean shutdown of the connection
  pool. Without it, connections may linger until the OS reclaims them.
- `create_tables()` uses `run_sync(ModelBase.metadata.create_all)` internally — the
  bridge that lets sync SQLAlchemy DDL run inside an async context.
- The `lifespan` hook replaced the older `@app.on_event("startup")` / `on_event("shutdown")`
  decorators, which are deprecated. `lifespan` is preferable because it makes the
  startup/shutdown pair a single, lexically bounded unit.
