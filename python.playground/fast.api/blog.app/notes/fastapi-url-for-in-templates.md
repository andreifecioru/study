# Generating URLs in Jinja2 Templates with `url_for()`

Rather than hardcoding URL paths in templates, FastAPI exposes a `url_for()` helper that generates URLs by **route name** or **mounted app name**. This decouples your templates from your URL structure — if a route path changes, the template doesn't need to. It's the same principle as Django's `{% url %}` or Rails' path helpers: names are stable contracts, paths are implementation details.

## Examples

```jinja2
{# Link to a named route #}
<a href="{{ url_for('post', post_id=post.id) }}">Read more</a>

{# Static file reference via mounted StaticFiles (name="static") #}
<link rel="stylesheet" href="{{ url_for('static', path='style.css') }}">
```

Routes are named via the `name` parameter on the decorator:
```python
@app.get("/posts/{post_id}", name="post")
def show_post(post_id: int): ...
```

## Query Parameters

Pass them as extra keyword arguments beyond the path params — FastAPI appends anything that doesn't match a `{param}` in the route as a query string:

```jinja2
{# Route: /posts — produces /posts?published=true #}
{{ url_for('get_posts', published='true') }}

{# Route: /posts/{post_id} — produces /posts/1?highlight=intro #}
{{ url_for('post', post_id=1, highlight='intro') }}
```

## Caveats

- `url_for()` is only available in templates when `request` is passed into the template context — Jinja2 gets it via the `Request` object FastAPI injects.
- For static files, the name matches the `name` argument in `app.mount("/static", StaticFiles(...), name="static")`.
- Path parameters must be passed as keyword arguments matching the route's `{param}` names.
