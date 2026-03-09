# `typeof` — Runtime Operator vs. Type-Level Operator

`typeof` exists in two completely separate worlds. In a value position it's the JS runtime operator (returns `"string"`, `"number"`, etc.). In a type position it's a TypeScript-only operator that extracts the *type* of a value — erased at compile time, never in the emitted JS. The practical payoff: derive types from existing values without writing them twice. If the value changes shape, the derived type updates automatically.

```ts
const config = { host: "localhost", port: 8080 }

// Derive a type from a value — stays in sync automatically
type Config = typeof config  // { host: string; port: number }

// Derive a type from a function's return value — function is the source of truth
function makeUser() {
  return { name: "John", age: 25 }
}
type User = ReturnType<typeof makeUser>  // { name: string; age: number }
```

**Caveats:**
- `typeof` in a type position only works on identifiers and their property chains — not expressions like `typeof (a + b)`.
- It reflects the *inferred* type, so a `let` variable gives you the widened type (`number`, not `42`).
- `ReturnType<typeof fn>` — no `()`. You pass the type of the function, not a call to it.
- Particularly useful when a function is the canonical definition of a shape and you don't want to maintain a separate interface alongside it.
