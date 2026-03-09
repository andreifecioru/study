# `keyof` + Index Types — Generic Property Access

`keyof T` produces the union of an object type's property names as string literals. `T[K]` (index type) gives you the type of property `K` on `T`. Together they enable type-safe property access — but there are two distinct forms with different precision.

```ts
interface Person { name: string; age: number }

// Form 1 — non-generic: return type is the union of all value types (string | number)
// Return type stays correct as Person evolves, but callers lose per-key precision
function getProp(key: keyof Person, person: Person): Person[keyof Person] {
  return person[key]
}

// Form 2 — generic: return type is resolved per call site
// getProp("age", john)  → number
// getProp("name", john) → string
function getProp<K extends keyof Person>(key: K, person: Person): Person[K] {
  return person[key]
}
```

**The broken middle ground (linter catches it):**
```ts
// K is declared but key is typed as keyof Person — K can't be inferred, it's useless
function getProp<K extends keyof Person>(key: keyof Person, person: Person): Person[K]
//                                            ^^^ should be K, not keyof Person
```
`@typescript-eslint/no-unnecessary-type-parameters` flags this: a type parameter used only once in the signature provides no value.

**Other useful index type patterns:**
```ts
// Union of all value types in T
type PersonValues = Person[keyof Person]  // string | number

// Element type of an array — number is the index key for arrays
type Arr = string[]
type Element = Arr[number]  // string

type arr = [1, 2, 3, true]
type Element = (typeof arr)[number] // number | boolean
type FirstElement = (typeof arr)[0] // number

// Chained access — works on nested types
type Street = Address["location"]["street"]
```

**Caveats:**
- Form 1 is simpler and often enough — use it when callers don't need the exact return type.
- Form 2 is the right choice when the return value will be used in a type-sensitive context (assigned to a typed variable, passed to another typed function, etc.).
- The constraint `K extends keyof Person` is what makes `Person[K]` safe — without it, TypeScript can't guarantee `K` is a valid key.
- `T[number]` is the idiomatic way to extract the element type of an array — used heavily in utility types and generic constraints.
