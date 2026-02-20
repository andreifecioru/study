# Python `find()` via `next()` + Generator

Python lists have no built-in `find()` method. The idiomatic functional equivalent is `next()` combined with a generator expression.

## Pattern

```python
result = next((item for item in collection if item.id == target_id), None)
```

## Why it works

- The generator expression is **lazy** — it doesn't evaluate the whole list upfront
- `next()` **short-circuits** on the first match, same as a `break` in a for loop
- The second argument to `next()` is the **default** returned when nothing matches (avoids `StopIteration`)

## Compared to alternatives

| Approach | Returns | Short-circuits? |
|---|---|---|
| `next(gen, None)` | First match or `None` | Yes |
| `filter(fn, list)` | Iterator of all matches | No |
| `for` loop with `break` | First match | Yes |

## Notes

- Prefer this over `filter()` when you only need the first match
- If you need all matches, use a list comprehension or `filter()`
- Common convention in Python codebases — worth recognising on sight
