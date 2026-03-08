# Literal Types, Type Widening, and `as const`

TypeScript infers the *narrowest* type it can justify. For `const` primitives, that's the literal value itself — reassignment is impossible, so the type can be exact. For `let` and object properties, TypeScript *widens* to the primitive (`number`, `string`, etc.) because mutation is possible and the type must accommodate future values. `as const` is the escape hatch: it signals deep immutability, allowing TypeScript to keep literal types throughout an object or array. Think of it like Python's frozen dataclasses — but only at the type level; there's no runtime enforcement.

```ts
const a = 4           // type: 4  (literal — can't be reassigned)
let b = 4             // type: number (widened — could be reassigned)

const obj = { x: 4 }         // type: { x: number } (property is mutable)
const frozen = { x: 4 } as const  // type: { readonly x: 4 } (literal preserved)

// Practical use: constrained value sets
type Direction = "north" | "south" | "east" | "west"
```

**Caveats:**
- `as const` adds `readonly` to every property recursively — it's deep, unlike a shallow freeze.
- It's purely compile-time. Unlike `Object.freeze()`, `as const` does not prevent mutation at runtime — it just makes TypeScript treat the values as immutable.
- For arrays, `as const` produces a `readonly` tuple with literal element types rather than a mutable array type.
