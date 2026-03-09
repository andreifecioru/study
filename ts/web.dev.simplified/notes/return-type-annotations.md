# Return Type Annotations — Contract vs. Noise

TypeScript's inference is strong enough that return types on most functions are redundant noise. The common advice is to omit them and let inference do the work. But this misses something important: a return type annotation is a *contract at the function boundary* — a compile-time test that says "this function must return X." Without it, a type mismatch is reported at call sites, not at the function definition. In large codebases that means chasing errors to their actual source. The right rule isn't "always annotate" or "never annotate" — it's *annotate where the type is part of the interface, omit where it's an implementation detail*.

```ts
// Omit — simple, obvious, inference is unambiguous
const double = (n: number) => n * 2

// Annotate — exported function, multiple return paths, type is part of the API contract
export function parseConfig(raw: string): Config {
  if (!raw) return defaultConfig   // error caught here if type is wrong
  return JSON.parse(raw)
}

// Annotate — forces you to think; prevents accidental shape changes from leaking out
function buildUser(data: RawData): User { ... }
```

**Caveats:**
- The TypeScript community generally defaults to *omit*; mypy/Python defaults to *annotate* — the difference reflects that Python's type system was retrofitted, while TypeScript's inference was a first-class design goal from day one.
- `@typescript-eslint` has `explicit-function-return-type` (all functions) and `explicit-module-boundary-types` (exported functions only) to enforce this where desired. The latter is the practical middle ground.
- For callbacks and short internal helpers, omitting is almost always the right call.
