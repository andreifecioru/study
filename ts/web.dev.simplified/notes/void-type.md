# The `void` Type — "Return Value Won't Be Used", Not "Returns Nothing"

`void` doesn't mean a function is incapable of returning a value — it means the caller won't use whatever is returned. This distinction matters when `void` appears in a *function type* (a callback signature): TypeScript allows a concrete function that returns a value to satisfy a `void`-returning function type, because the contract only says the return value will be ignored, not that it must be absent. This is intentional — it makes higher-order functions like `forEach` work correctly with callbacks that happen to return something.

```ts
type Callback = () => void

// A function returning a value can satisfy a void function type
const cb: Callback = () => 42  // valid — return value is just ignored

// forEach's callback is typed as () => void
// so this works even though push returns a number:
const result: number[] = []
;[1, 2, 3].forEach(x => result.push(x))  // push returns number, void expected — fine

// But a function *declared* with void return cannot return a value:
function doWork(): void {
  return 42  // Error — explicit void annotation means "don't return anything"
}
```

**Caveats:**
- The permissive behavior only applies to `void` in *function types* (callbacks, type aliases). An explicit `(): void` annotation on a concrete function declaration still disallows returning a value.
- `void` and `undefined` are related but not the same. A `void` function *may* return `undefined` implicitly, but `void` is not assignable to `undefined` in all contexts.
- If you want to truly enforce "no return value", the explicit return annotation on the function declaration is what does it — not the callback type.
