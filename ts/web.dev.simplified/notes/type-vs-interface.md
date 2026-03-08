# `type` vs `interface` — Real Differences Beyond "Basically the Same"

Both describe object shapes and are largely interchangeable for that purpose, but they differ in capability and intent. `interface` is *open* — it can be declared multiple times and TypeScript merges the declarations. `type` is *closed* — a duplicate declaration is an error. More importantly, `type` is more expressive: it can represent unions, intersections with primitives, tuples, and mapped types — things `interface` simply cannot do. The community convention that has emerged: use `interface` for object shapes you expect to be extended or implemented; use `type` for everything else.

```ts
// Declaration merging — only interfaces can do this
interface Window { myCustomProp: string }  // augments the global Window type

// Expressiveness — only types can do this
type ID = string | number
type Pair = [string, number]
type Nullable<T> = T | null

// Both work for object shapes
interface User { name: string; age: number }
type User = { name: string; age: number }  // equivalent
```

**Caveats:**
- Declaration merging is mainly relevant for library authoring and ambient type augmentation (e.g. extending `Window`, augmenting third-party module types). Rare in application code.
- `interface` tends to produce cleaner TypeScript error messages — it's referred to by name rather than expanded inline.
- Preferring `type` everywhere (as some developers do) is defensible but slightly against community convention. Know the tradeoffs before picking a camp.
