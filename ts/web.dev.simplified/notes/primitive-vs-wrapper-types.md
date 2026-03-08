# Primitive Types vs Wrapper Object Types (number vs Number)

TypeScript faithfully models a JavaScript quirk: every primitive (`number`, `string`, `boolean`) has a corresponding wrapper object (`Number`, `String`, `Boolean`) created via `new Number(42)` etc. These wrapper objects exist for historical reasons and are almost never used intentionally. TypeScript exposes both as distinct types — lowercase for the primitive value, uppercase for the object. Always use lowercase in annotations; the uppercase versions are a trap.

```ts
// Always use these:
const a: number = 42
const b: string = "hello"
const c: boolean = true

// Never use these:
const x: Number = 42      // works, but wrong — wrapper object type
const y: String = "hello" // same problem

// Why it matters at runtime:
typeof 42            // "number"
typeof new Number(42) // "object"
42 === new Number(42) // false
```

**Caveats:**
- TypeScript does allow assigning a primitive to an uppercase type (so `Number = 42` compiles), making the mistake easy to miss without a linter.
- `@typescript-eslint` has a rule (`ban-types`) that flags the uppercase versions — worth enabling.
- The uppercase types *do* appear legitimately in TS's own lib types (e.g. `Number.isFinite`), but that's the constructor object, not an annotation.
