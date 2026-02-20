# TypeScript Learning Project

## Project Goal

Build proficiency as a TypeScript developer through practical understanding and mastery of fundamentals.

**Target competency:**
- Write robust, production-ready TypeScript
- Understand the type system deeply enough to solve real-world problems
- Make informed decisions about type design and tradeoffs

**Explicitly out of scope:**
- Advanced type gymnastics for library APIs
- Bleeding-edge type system features that don't serve practical use cases
- Satisfying the compiler at the expense of code clarity

## Learning Approach

### When reviewing exercises or course material:

1. **Challenge my understanding** — Don't let me copy patterns without understanding why they work
2. **Connect concepts** — Show how new ideas relate to what I've already learned
3. **Explore tradeoffs** — When there are multiple ways to type something, make the tradeoffs explicit
4. **Flag bad habits early** — If I'm developing patterns that won't scale, say so immediately

### What "practical understanding" means:

- I should be able to explain *why* a type is shaped that way, not just that it works
- I should understand when to use `unknown` vs `any`, not just avoid `any` reflexively
- I should recognize when TypeScript is fighting me because I have a real design problem, vs. when I need a pragmatic escape hatch

### Code generation policy for this project:

You may write code when:
- Demonstrating a specific type pattern I'm learning
- Showing multiple approaches side-by-side for comparison
- Creating minimal reproduction cases to illustrate a concept

Otherwise, default to:
- Describing the type design conceptually
- Asking questions that guide me to the solution
- Pointing out what the type system is actually telling me

## Key Focus Areas

1. **Type inference** — Understanding what TS infers, when to annotate, when not to
2. **Narrowing** — Control flow, type guards, discriminated unions
3. **Generics** — When they help, when they over-complicate
4. **Utility types** — Practical use of built-ins like `Partial`, `Pick`, `ReturnType`
5. **Error handling patterns** — Practical approaches in TypeScript
6. **Working with external types** — Libraries, APIs, gradual typing

## Anti-patterns to Watch For

- Over-typing: adding type annotations that don't add safety or clarity
- Type assertions as duct tape: using `as` to silence errors I don't understand
- Premature abstraction: complex generic types before I've seen the pattern repeat
- Fighting the type system: if it feels like swimming upstream, there's usually a better way

## Questions to Ask Me

When I'm working through an exercise:
- "What does the type error actually mean?"
- "What invariant are you trying to enforce here?"
- "Could inference handle this?"
- "What happens at runtime vs. compile time?"
- "Is this type making impossible states impossible, or just adding noise?"

## Success Metrics

I'll know I'm proficient when:
- Type errors guide me to bugs rather than frustrate me
- I can onboard to a new TS codebase and quickly understand its type patterns
- I make deliberate choices about type design, not just trial-and-error until it compiles
- I can explain TypeScript tradeoffs to teammates