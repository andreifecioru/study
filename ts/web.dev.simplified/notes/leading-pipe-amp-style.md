# Leading pipe / amp - Formatting for Unions and Intersections

When a union or intersection type spans multiple lines, putting the operator (`|` or `&`) at the start of each member makes every member visually parallel and diffs cleanly — adding or removing a member is a single-line change with no trailing punctuation to fix. The leading operator on the first member is syntactically optional but keeps the pattern consistent.

```ts
// union — common when members are long or numerous
type Result =
  | { status: "success"; data: string }
  | { status: "error"; message: string }
  | { status: "idle" }

// intersection — same trick, less common (fewer members usually)
type AdminUser =
  & User
  & Admin
  & { createdAt: Date }
```

**Caveats:**
- Purely a style convention — both forms are identical to the compiler.
- Prettier enforces the leading-pipe style automatically when a union wraps to multiple lines.
- Leading `&` for intersections is valid but rarely seen in practice; most intersections stay on one line.
