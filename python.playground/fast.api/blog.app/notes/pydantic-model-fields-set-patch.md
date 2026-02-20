# Partial Updates with `model_fields_set`

Pydantic populates `model_fields_set` with the names of fields that were explicitly present
in the incoming payload — fields that fell back to their defaults are excluded. This lets you
distinguish "the client didn't send this field" from "the client sent null for this field",
which `is not None` checks cannot do. It's the correct primitive for implementing PATCH
semantics: apply only what was provided, leave everything else untouched.

## Pattern

```python
# Generic patch: apply only the fields the client actually sent
for field in patch_data.model_fields_set:
    setattr(model_instance, field, getattr(patch_data, field))
```

## When `is not None` falls short

| Client sends        | `field` value | `is not None` | `field in model_fields_set` |
|---------------------|---------------|---------------|-----------------------------|
| `{}`                | `None` (default) | False      | False                       |
| `{"field": null}`   | `None` (explicit) | False     | **True**                    |

With `is not None`, both cases look identical. With `model_fields_set`, you can act on an
explicit null — useful for nullable fields the client wants to clear.

## Caveats

- If your schema rejects `null` via type constraints (e.g. `str` without `| None`), the
  distinction is moot — Pydantic will raise a validation error before you ever see the value.
- The `setattr` loop assumes schema field names match model attribute names. If they diverge
  (e.g. via `alias`), you'll need to map them explicitly.
