# Pandas Grouping and Aggregation

**Date:** 2026-05-02
**Context:** Overview of how to use `groupby` in Pandas 2.0+, specifically focusing on the requirement for explicit numeric selection during aggregation.

---

## Key Concept: The `numeric_only` Requirement

In modern Pandas (2.0+), aggregator functions like `.mean()`, `.sum()`, or `.median()` no longer automatically drop non-numeric columns. If a DataFrame contains strings or other non-numeric types, you must explicitly set `numeric_only=True` to avoid a `TypeError`.

## Implementation Example

```python
import pandas as pd

# 1. Create a mixed-type DataFrame
products = [
    ["Sports", "Table tennis", 125.0, True],
    ["Sports", "Soccer ball", 18.99, True],
    ["Clothing", "Men's shirt", 25.0, True],
    ["Electronics", "TV", 255.0, True],
]

df = pd.DataFrame(
    data=products, 
    columns=["category", "name", "price", "availability"]
)

# 2. Group by category and calculate mean
# Without numeric_only=True, this raises: TypeError: dtype 'str' does not support operation 'mean'
avg_prices = df.groupby("category").mean(numeric_only=True)

display(avg_prices)
```

## Common Aggregators
- `.mean()`: Arithmetic mean.
- `.sum()`: Sum of values.
- `.count()`: Count of non-NA values (works on all types).
- `.min()` / `.max()`: Minimum/Maximum values.
- `.std()` / `.var()`: Standard deviation/Variance.

---

**Tags:** #pandas #data-analysis #groupby #aggregation #python
