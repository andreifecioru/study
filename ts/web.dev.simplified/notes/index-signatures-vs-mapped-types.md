# Index Signatures vs Mapped Types

Both let you define object types with dynamic keys, but they differ in precision. An index signature (`[key: Type]`) says "any key of this type" — it's open-ended and TypeScript won't enforce which specific keys exist. A mapped type (`[key in Union]`) iterates over a known union and creates exactly one property per member — closed and exhaustive. If you add a new member to the union, TypeScript will flag any mapped type that doesn't account for it.

```ts
type Person = { name: string; age: number; skillLevel: "Beginner" | "Intermediate" | "Expert" }

// Index signature — open-ended: any number key is valid
type GroupByAge = {
  [age: number]: Person[]
}

// Mapped type — closed: exactly these three keys, no more, no less
type GroupBySkill = {
  [skill in Person["skillLevel"]]: Person[]
}

// Equivalent shorthand using Record
type GroupBySkill = Record<Person["skillLevel"], Person[]>
```

**Caveats:**
- The label in an index signature (`age` in `[age: number]`) is purely cosmetic — only the type matters.
- The `in` keyword in a mapped type is not the same as JS `in` — it's a type-level iteration over a union.
- Mapped types are the foundation of built-in utility types: `Partial<T>`, `Required<T>`, `Readonly<T>`, and `Record<K, V>` are all mapped types.
- Prefer mapped types over index signatures when the key set is known and finite — you get exhaustiveness checking for free.
