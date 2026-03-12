# `Pick` and `Omit` — Deriving Subtypes

Both utility types derive a new type from an existing one without repeating property definitions. `Pick<T, K>` keeps only the listed keys; `Omit<T, K>` drops them. They're inverses — use whichever requires listing fewer keys. The practical value is that the derived type stays in sync with the source: if `User` changes, `NewUser` updates automatically.

```ts
type User = {
  id: string
  firstName: string
  lastName: string
}

type NewUser = Omit<User, "id">
// equivalent to:
// type NewUser = Pick<User, "firstName" | "lastName">

function saveUser(newUser: NewUser): User {
  return { ...newUser, id: crypto.randomUUID() }
}
```

**Both are shallow — they only operate on top-level keys:**
```ts
type User = { id: string; address: { street: string; city: string } }

// Can't omit a nested key — this doesn't work:
// type WithoutCity = Omit<User, "address.city">

// Manual workaround — compose using index types:
type WithoutCity = Omit<User, "address"> & {
  address: Omit<User["address"], "city">
}
```
The same shallowness applies to `Partial`, `Required`, and `Readonly` — they don't recurse into nested object types. Deep variants (e.g. `DeepReadonly`) aren't built in and require conditional types.

**Other caveats:**
- Prefer `Omit` when excluding few keys from a large type; prefer `Pick` when selecting a small subset — minimise the list you maintain.
- `Omit<T, "typo">` won't error if `"typo"` isn't a key of `T`. `Pick` will. Use `Pick` when you want the compiler to catch key name mistakes.
