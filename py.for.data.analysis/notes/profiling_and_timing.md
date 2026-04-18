# Profiling Python Code with Magic Commands

In Jupyter/IPython, "magic commands" (prefixed with `%` or `%%`) provide built-in tools for timing and profiling code.

## 1. `%time` and `%%time` (One-Shot Timing)

These commands measure the execution time of a **single run**.

- **`%time` (Line Magic):** Times a single line of code.
- **`%%time` (Cell Magic):** Times the entire cell.

### Metrics Explained
- **User Time:** Time the CPU spent running your code.
- **System Time:** Time the CPU spent on kernel-level tasks (like I/O).
- **Wall Time:** The total real-world time that passed (clock time).

**Best for:** Measuring long-running tasks where running it multiple times would be impractical (e.g., loading a large dataset or training a model).

---

## 2. `%timeit` and `%%timeit` (Statistical Benchmarking)

These commands run the code **many times** and calculate the average. This eliminates noise from background system processes.

- **`%timeit` (Line Magic):** Benchmarks a single line.
- **`%%timeit` (Cell Magic):** Benchmarks the entire cell.

### Key Characteristics
- **Accuracy:** Much more precise than `%time`.
- **Automatic Scaling:** It automatically decides how many loops to run based on how fast the code is.
- **Isolation:** Variables created inside a `%%timeit` cell are **not** saved to the global notebook scope by default.

**Best for:** Comparing the efficiency of two different algorithms or data structures (e.g., NumPy vs. List comprehensions).

---

## 3. Quick Reference Table

| Magic | Scope | Accuracy | Use Case |
| :--- | :--- | :--- | :--- |
| `%time` | Line | Low | Quick one-off check |
| `%%time` | Cell | Low | Measuring a long pipeline |
| `%timeit` | Line | High | Scientific benchmarking |
| `%%timeit` | Cell | High | Comparing algorithm speeds |

## 4. Tips for Accurate Profiling

1. **Avoid I/O in Benchmarks:** Network calls or disk reading vary too much for `%timeit` to be useful.
2. **Beware of Caching:** If your function caches results, the second run will be artificially fast.
3. **Use the `-o` flag:** If you need to access the results of a `%%timeit` run programmatically:
   ```python
   results = %%timeit -o
   print(results.best)
   ```
