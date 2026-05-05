# Matplotlib Reference Guide

Two main approaches: **Functional API** (pyplot module) and **Object-oriented API** (Figure/Axes objects).

## Imports

```python
import matplotlib.pyplot as plt
import numpy as np
```

## Functional API

Simple state-based interface using `plt.` functions. Maintains implicit current figure/axes.

### Basic Plot

```python
plt.plot(x, y)
plt.xlabel("X Label")
plt.ylabel("Y Label")
plt.title("Square function")
```

### Multi-plots on Same Canvas

```python
plt.subplot(1, 2, 1)  # args: rows, cols, plot_number
plt.plot(x, x ** 3, "r")

plt.subplot(1, 2, 2)
plt.plot(x, x ** 2, "b")
```

---

## Object-oriented API

Create Figure and Axes objects explicitly, then call methods on them. Preferred for complex plots.

### Basic Plot with Axes

```python
fig = plt.figure()

# order: left, bottom, width, height (all as 0-1 fractions of figure)
axes = fig.add_axes((0.1, 0.1, 0.8, 0.8))

axes.plot(x, y)
axes.set_xlabel("X Label")
axes.set_ylabel("Y Label")
axes.set_title("Square function")
```

### Multi-plot: Graph-in-Graph (Inset)

```python
fig = plt.figure()

axes1 = fig.add_axes((0.1, 0.1, 0.8, 0.8))  # main plot
axes2 = fig.add_axes((0.2, 0.5, 0.4, 0.3))  # inset plot

axes1.plot(x, y, "r")
axes2.plot(y, x, "g")
```

### Subplots (Recommended for Multi-plot)

```python
fig, axes = plt.subplots(nrows=1, ncols=2)

axes[0].plot(x, y, "r")
axes[0].set_title("1st plot")

axes[1].plot(y, x, "g")
axes[1].set_title("2nd plot")

plt.tight_layout()  # prevents overlapping
```

### Figure Size and DPI

```python
fig, axes = plt.subplots(
    nrows=2,
    ncols=1,
    figsize=(8, 4),  # width, height in inches
    dpi=300
)
```

---

## Legends

**Required:** Add `label=` parameter to each plot, then call `plt.legend()`.

```python
fig = plt.figure()
axes = fig.add_axes((0, 0, 1, 1))

axes.plot(x, x ** 2, label="X Squared")
axes.plot(x, x ** 3, label="X Cubed")

plt.legend(loc="best")  # auto-best location
```

---

## Styling Plots

```python
axes.plot(
    x, y,
    color="purple",       # or hex: "#FF5733"
    linewidth=3,         # float, lower = thinner
    alpha=0.5,           # transparency (0-1)
    linestyle="--",      # "-", "--", ":", "-."
    marker="o",          # "o", "s", "^", "D", etc.
    markersize=10,
    markerfacecolor="orange",
    markeredgewidth=2,
    markeredgecolor="black"
)
```

### Common Color Abbreviations

| Code | Color |
|------|-------|
| `r` | red |
| `b` | blue |
| `g` | green |
| `k` | black |
| `m` | magenta |
| `c` | cyan |
| `y` | yellow |

---

## Axes Control and Plot Ranges

```python
# Generate data
x = np.linspace(0, 20, 200)
y = x ** 2

fig = plt.figure()
axes = fig.add_axes((0, 0, 1, 1))
axes.plot(x, y, color="blue", linestyle="--")

# Zoom into specific region
axes.set_xlim((0, 5))
axes.set_ylim((0, 30))
```

---

## Saving Plots

Matplotlib infers format from file extension.

```python
fig.savefig("plot.png")        # PNG
fig.savefig("plot.pdf")       # PDF
fig.savefig("plot.svg")        # SVG
```

### Key Parameters

```python
fig.savefig(
    "output.png",
    dpi=300,           # resolution
    bbox_inches="tight"  # trim whitespace
)
```

---

## Layout Control

### tight_layout()

Automatically adjusts subplot spacing to prevent overlaps.

```python
plt.tight_layout()  # call after all plots, before save
```

**Note:** Not compatible with `add_axes` filling entire figure `(0, 0, 1, 1)`.

### Manual Adjustment

```python
fig.subplots_adjust(
    left=0.1,
    right=0.9,
    bottom=0.1,
    top=0.9,
    wspace=0.3,   # width spacing between subplots
    hspace=0.3    # height spacing between subplots
)
```

---

## Quick Reference

| Task | Functional API | OO API |
|------|----------------|-------|
| Create figure | `plt.figure()` | `fig = plt.figure()` |
| Create axes | `plt.subplot()` | `fig.add_axes()` |
| Add label | `plt.xlabel()` | `axes.set_xlabel()` |
| Add title | `plt.title()` | `axes.set_title()` |
| Add legend | `plt.legend()` | `axes.legend()` |
| Set xlim | `plt.xlim()` | `axes.set_xlim()` |
| Save | `plt.savefig()` | `fig.savefig()` |

---

## Tips

1. **In Jupyter/VS Code:** `%matplotlib inline` usually not needed - plots render automatically
2. **Prefer OO API** for complex figures - better control and readability
3. **Always call `tight_layout()` after** creating subplots, before saving
4. **Use `label=` parameter** for legends to work
5. **Check kernel cwd** if `savefig` fails with `FileNotFoundError`