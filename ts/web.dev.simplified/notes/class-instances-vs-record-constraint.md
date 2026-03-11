# Class Instances Fail `T extends Record<string, unknown>`

`Record<string, unknown>` requires an **index signature** — TypeScript must be able to say "any string key on this type returns `unknown`". Plain object types and interfaces satisfy this structurally because their properties are a subset of that. Class instances do not — TypeScript treats class properties as specific named members, not as an open index signature, so a class type is not assignable to `Record<string, unknown>` even if all its properties would individually type-check as `unknown`.

```ts
interface User { name: string; age: number }
class UserClass { name: string = ""; age: number = 0 }

interface APIResponse<T extends Record<string, unknown> = Record<string, unknown>> {
  data: T
}

type A = APIResponse<User>       // ✓ — interface satisfies Record<string, unknown>
type B = APIResponse<UserClass>  // ✗ — class instance lacks an index signature
```

**Caveats:**
- `T extends object` is the looser alternative — it accepts class instances, arrays, and anything non-primitive, but loses the "plain object" guarantee.
- This distinction matters in practice: JSON deserialization always produces plain objects, never class instances — so `extends Record<string, unknown>` is actually the more honest constraint for API response types.
- The surprise is that two types that look structurally identical (interface vs class with the same properties) behave differently under this constraint.
