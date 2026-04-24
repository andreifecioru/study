# Pandas Data Structures: Series, DataFrames, and the Index

## Core Mental Model
Pandas is built on top of NumPy, but it adds a critical layer: **Labels**.
- **Series:** A 1D array with an Index (labels).
- **DataFrame:** A 2D table where each column is a Series, all sharing the same Index.

## The Index: The "Secret Sauce"
Unlike NumPy's implicit integer positions ($0, 1, 2...$), a pandas Index is a first-class object that provides **label-based mapping**.

### Nature of the Index
An Index can contain almost any **hashable** Python object:
- **Integers/Strings:** The most common.
- **DateTime:** Enables powerful time-series alignment.
- **Tuples:** Enables hierarchical (`MultiIndex`) structures.
- **Custom Objects:** As long as they implement `__hash__` and `__eq__`.

#### Example: Custom Objects as Labels
```python
import pandas as pd

class Project:
    def __init__(self, id, code):
        self.id = id
        self.code = code
    
    def __hash__(self):
        return hash((self.id, self.code))
    
    def __eq__(self, other):
        return (self.id, self.code) == (other.id, other.code)
    
    def __repr__(self):
        return f"Proj({self.code})"

p1 = Project(1, "ALPHA")
p2 = Project(2, "BETA")

df = pd.DataFrame({"budget": [1000, 2000]}, index=[p1, p2])

# Retrieval by object hash
print(df.loc[p1]) 
```


### Retrieval Mechanism
Pandas maintains an internal **hash table** mapping labels to their integer positions.
- **Label Lookup (`.loc`):** $O(1)$ average time complexity.
- **Positional Lookup (`.iloc`):** Access by integer offset ($0$ to $N-1$).

---

## The Syntax "Gotcha": `[]` vs `.loc` vs `.iloc`

Because pandas tries to be convenient, the indexing operator `[]` is polymorphic, which can lead to ambiguity.

### 1. The Indexing Operator: `df[key]`
Primarily for **Columns**.
- `df['A']`: Returns the **column** named 'A'.
- If 'A' is a row label, `df['A']` will raise a `KeyError`.
- **Exception:** If you pass a slice (`df[0:5]`) or a boolean mask (`df[df['A'] > 0]`), it operates on **rows**.

### 2. Label-based Accessor: `df.loc[label]`
Exclusively for **Rows** (and optionally columns as the second argument).
- `df.loc['A']`: Returns the **row** with label 'A'.
- `df.loc['A', 'col1']`: Returns the value at row 'A', column 'col1'.
- This is the explicit way to perform label-based retrieval.

### 3. Position-based Accessor: `df.iloc[position]`
Exclusively for **Integer Positions**.
- `df.iloc[0]`: Returns the first row, regardless of its label.
- Useful when the label itself is an integer (e.g., label is `100`, but it's at position `0`).

## Summary Rule of Thumb
| Operation | Syntax | Target |
| :--- | :--- | :--- |
| **Get Column** | `df['col_name']` | Column |
| **Get Row by Label** | `df.loc['label']` | Row |
| **Get Row by Position** | `df.iloc[0]` | Row |
| **Filter Rows** | `df[mask]` | Row |

---

## The Axis Mental Model
One of the biggest hurdles in pandas (especially coming from Spark) is the `axis` parameter.

| Axis | Name | Direction | Memory Aid |
| :--- | :--- | :--- | :--- |
| **0** | `index` | **Vertical** (Down the rows) | The default for most operations. |
| **1** | `columns` | **Horizontal** (Across columns) | The number `1` looks like a vertical column. |

### Case Study: `df.drop()`
- **Old/Ambiguous Way:** `df.drop("T", axis=1)` (Requires remembering axis numbers).
- **Modern/Explicit Way:** `df.drop(columns="T")` (Self-documenting).

### Immutability & Reassignment
Like Spark, but unlike Excel, `df.drop()` **does not modify the DataFrame in place**. It returns a new object.
```python
# WRONG: df remains unchanged
df.drop(columns="T") 

# CORRECT: Reassign the result
df = df.drop(columns="T")
```

