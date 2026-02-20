# Serving Static Files in FastAPI via `app.mount()`

FastAPI doesn't serve static files (CSS, JS, images) out of the box — you explicitly "mount" a separate ASGI sub-application at a given URL prefix. `StaticFiles` is that sub-application: it intercepts all requests under the mounted path and maps them to files on disk. This is a deliberate design choice — FastAPI is an API framework first, and static file serving is treated as an add-on, not a default.

## Example

```python
from fastapi.staticfiles import StaticFiles

app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")
```

A request to `/static/style.css` will serve `./static/style.css` from disk.

## Tradeoffs / Caveats

- **Mounted apps are opaque to FastAPI** — routes under `/static` don't appear in the OpenAPI schema and can't use FastAPI middleware or dependencies.
- **Order matters** — mount before defining routes if paths could overlap (though `/static` rarely conflicts).
- **Not for production at scale** — in production, a reverse proxy (Nginx, Caddy) should serve static files directly, bypassing Python entirely. `StaticFiles` is convenient for development.
- The `name` parameter enables URL generation via `request.url_for("static", path="style.css")` in templates — useful for cache-busting or CDN swaps later.
