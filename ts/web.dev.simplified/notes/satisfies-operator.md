# `satisfies` — Validate Without Widening

Type annotations and `satisfies` both check that a value conforms to a type, but they differ in what type the variable ends up with. An annotation *widens* the inferred type to the annotation — you lose specifics. `as` skips the check entirely. `satisfies` gives you the best of both: it validates the shape, then preserves the more specific inferred type. It also provides contextual typing, so TypeScript uses the target type to guide inference of inner expressions (e.g. inferring tuples instead of arrays).

```ts
const john = [
  ["name", "John"],  // inferred as [string, string], not [string, unknown]
  ["age", 30],       // inferred as [string, number], not [string, unknown]
] satisfies [string, unknown][]

// With annotation `: [string, unknown][]` — specifics are lost, john[0][1] is unknown
// With `as [string, unknown][]`          — no check, unsafe
// With `satisfies [string, unknown][]`   — check passes, specific types preserved
```

**Caveats:**
- Added in TypeScript 4.9.
- `satisfies` does not change the runtime value — purely a compile-time check.
- Most useful when you want to constrain a value to a shape but still retain precise types for downstream use.
- In this specific scenario (`as const` + `satisfies` vs just `satisfies`): `as const` is not needed. `satisfies` already provides contextual typing that guides tuple inference. `as const` would additionally make the arrays `readonly` and freeze the literal types — overkill unless you specifically need immutability.
