# Selective `Partial` and `Required` — Per-Key Optionality

`Partial<T>` and `Required<T>` are all-or-nothing — they apply to every key in the type. To selectively make specific keys optional or required while leaving the rest unchanged, the pattern is: `Pick` the keys you want to transform, apply the utility, then intersect back with the rest of the type. In intersections, `required` overrides `optional` — `{ email: string } & { email?: string }` resolves to `{ email: string }`.

```ts
type User = { id: number; username: string; email?: string }

// Make specific keys required, leave the rest as-is
type RequiredPick<T, K extends keyof T> = Required<Pick<T, K>> & T

// Make specific keys optional, force everything else to be required
type PartialPick<T, K extends keyof T> = Partial<Pick<T, K>> & Required<Omit<T, K>>

type UserWithEmail   = RequiredPick<User, "email">    // email is now required
type UserWithoutName = PartialPick<User, "username">  // username is now optional
```

**Caveats:**
- The intersection trick works because required wins over optional when the same key appears in both sides.
- This is the general pattern for any per-key type transformation: `Pick` → transform → intersect with the remainder.
- `RequiredPick` intersects with the full `T` (not `Omit<T, K>`) — this is fine because the `Required<Pick<T, K>>` side wins for the targeted keys.
