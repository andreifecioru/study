# Pandas Multi-Indexing (Hierarchical Indexing)

**Date:** 2026-05-01
**Context:** Study notes on representing higher-dimensional data in 2D Pandas DataFrames.

---

## Key Concepts

Multi-indexing allows for multiple levels of labels on a single axis (rows or columns).

- **Dimensionality:** Enables the representation of 3D or higher data in a 2D table format.
- **Organization:** Groups related data together, reducing redundancy in the visual output.
- **Navigation:** Supports complex selection using nested `.loc` calls or cross-sections (`.xs`).

## Selection Methods

1. **Nested `.loc`:** Drill down through levels sequentially.
   ```python
   df.loc["OuterLevel"].loc["InnerLevel"]
   ```
2. **Cross-section (`.xs`):** Select data at a specific level without needing to specify the parent levels.
   ```python
   df.xs("LabelValue", level="level_name")
   ```

## Example: Product Catalog

```python
import pandas as pd

# Creating a Multi-Index from tuples
index_tuples = [
    ("Sports", "Ball"), ("Sports", "Glove"),
    ("Electronics", "TV"), ("Electronics", "PS5")
]
multi_index = pd.MultiIndex.from_tuples(index_tuples, names=["category", "product"])

data = [[18.99, True], [22.99, False], [255.0, True], [299.99, True]]
df = pd.DataFrame(data, index=multi_index, columns=["price", "availability"])

# Querying available products while preserving Multi-Index
available = df[df["availability"]]
```

---

**Tags:** #pandas #data-analysis #indexing #python
