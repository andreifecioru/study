# Python Iterators and Iterables

## 1. The Mental Model (Box vs. Finger)

- **Iterable (The Box):** A container that *holds* data but doesn't track current position. It is **reusable**.
- **Iterator (The Finger):** A stateful pointer that *moves through* the data. It is **one-shot (exhaustible)**.

## 2. The Iterator Protocol

The protocol defines how these two types interact through standard methods.

### The Iterable Object
- Defines `__iter__()`.
- When passed to `iter()`, it returns a **new** iterator.
- **Example:** `list`, `str`, `dict`, `CountingIterable`.

### The Iterator Object
- Defines `__next__()`. Returns the next item or raises `StopIteration`.
- Defines `__iter__()`. Must return `self` (making every iterator also an iterable).
- **Example:** `CountingIterator`, `enumerate`, `zip`.

## 3. The Lifecycle of a `for` Loop

When you run `for item in obj:`, Python follows this internal flow:
1.  **Call `iter(obj)`:** Obtains an iterator.
    - If `obj` is an **Iterable**, it creates a fresh iterator.
    - If `obj` is an **Iterator**, it returns itself.
2.  **Repeatedly call `next(it)`:** 
    - On each success, `item` is assigned the value.
3.  **Handle `StopIteration`:**
    - When the iterator is exhausted, the loop catches this exception and terminates gracefully.

## 4. Implementation Patterns (Inheritance)

In this project's `CountingIterator` example, we use `abc.Iterator`:
```python
class CountingIterator(abc.Iterator[int]):
    def __next__(self):
        # Implementation...
```
### Why use `abc.Iterator`?
- **Reduces Boilerplate:** It provides a default `__iter__` that returns `self`.
- **Formal Compliance:** It marks the class as an iterator for type checkers (like `mypy`) and `isinstance` checks.

## 5. Reusability vs. Exhaustion

- **Iterable (Reusable):** You can loop over it multiple times because each loop requests a *new* iterator (finger).
- **Iterator (Exhaustible):** You can loop over it only once. Once `StopIteration` is raised, the iterator is "dead". Subsequent loops will find it already at the end.

## 6. Generators and the Protocol

Generators are a concise way to implement the iterator protocol.

### Generators as Iterators
Any function containing the `yield` keyword returns a **generator object**. This object automatically implements the iterator protocol:
- It has a `__next__()` method (executes code until the next `yield`).
- It has an `__iter__()` method that returns `self`.
- It is **exhaustible** (one-shot).

### Implementing Iterables with Generators
You can use a generator inside an `__iter__` method to make a class iterable without writing a separate iterator class:

```python
class CountingIterable:
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def __iter__(self):
        # The generator handles the state and StopIteration for us
        curr = self.start
        while curr < self.end:
            yield curr
            curr += 1
```

This pattern is much cleaner than the `Iterable`/`Iterator` class pair because the generator maintains its own internal state and handles `StopIteration` automatically when the function returns.
