# `readonly` — Property vs. Contents

`readonly` in TypeScript is positional — it modifies whatever immediately follows it. On an object property, it prevents reassignment of the property itself. On an array type (`readonly number[]`), it prevents mutation of the array's contents (no `push`, `pop`, `splice`, etc.). These are independent constraints and can be combined.

```ts
type A = {
  readonly arr: readonly number[]
  //  ^--- property can't be reassigned
  //                ^--- array contents can't be mutated
}

const example: A = { arr: [1, 2, 3] }

example.arr = []      // error — property is readonly
example.arr.push(4)   // error — array is readonly
```

**Caveats:**
- Both are compile-time only — no runtime enforcement. The JS object is still mutable; TypeScript just won't let you write code that mutates it.
- `readonly number[]` is equivalent to `ReadonlyArray<number>` — same type, different syntax.
- A `readonly T[]` is assignable to a mutable `T[]`? No — the opposite. A mutable array is assignable to a readonly one (widening), but not vice versa. You can always *add* a readonly constraint, never silently remove one.
- The same pattern exists for other collection types: `ReadonlyMap<K, V>` and `ReadonlySet<T>` are built-in TypeScript types that strip all mutating methods (`set`, `delete`, `clear`). There's no `readonly Map<K, V>` keyword syntax — you must use the named type explicitly.
