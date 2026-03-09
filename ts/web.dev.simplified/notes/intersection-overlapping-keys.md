# Intersection Types with Overlapping Keys

When two object types share a key, `&` doesn't pick one or merge them — it intersects the field types. The resulting field type must satisfy *both* simultaneously. If the two field types are compatible (e.g. both `number`), the intersection is that type. If they're incompatible (e.g. `string` and `number`), the field becomes `never` — a type no value can satisfy. TypeScript won't error at the type definition site; the problem only surfaces when you try to construct a value.

```ts
type A = { x: number; name: string }
type B = { x: number; age: number }
type AB = A & B  // { x: number; name: string; age: number } — fine, x types match

type C = { x: string }
type D = { x: number }
type CD = C & D  // { x: string & number } = { x: never }

const val: CD = { x: ??? }  // impossible — no value satisfies x: never
```

**Caveats:**
- `never` fields make the whole type silently unusable — TypeScript won't flag the definition, only failed assignments.
- Intersection is safest on non-overlapping shapes. Use it to combine complementary types, not to merge types that share keys.
- This is different from `Object.assign` or spread semantics, where the last value wins. There is no "last value wins" at the type level — both constraints must hold.
