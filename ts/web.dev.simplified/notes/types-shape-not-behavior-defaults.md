# Types Describe Shape, Not Behavior — Default Values Belong in the Implementation

Types and interfaces describe what values *look like*, not what happens at runtime. A default value is runtime behavior — it only makes sense when code is executing and a value is absent. Since TypeScript's type system is fully erased before execution, there's no mechanism for a type to supply a value. The separation is intentional: the type captures the contract (this field may be absent), the implementation handles the consequence (here's what to use when it is).

```ts
type Config = {
  host: string
  port?: number  // optional: may be absent, but no default here
}

// Default in function signature:
function connect(config: Config = { host: "localhost" }) { ... }

// Default via destructuring — the neat trick:
function connect({ host, port = 8080 }: Config) {
  // port is number here, not number | undefined
}
```

**Caveats:**
- After destructuring with a default, TypeScript narrows the type — `port` becomes `number`, not `number | undefined`. The default value is the proof.
- This pattern only works at function boundaries. For object construction elsewhere, you need explicit logic (`?? defaultValue`) or a factory function.
- Don't conflate `?` (optional field) with "has a default." Optional just means the caller can omit it — what happens next is up to the implementation.
