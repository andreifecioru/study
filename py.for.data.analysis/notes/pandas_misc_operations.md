# Miscellaneous DataFrame Operations

This document captures additional commonly-used DataFrame operations that don't fit into the main categories (sanitization, grouping, merging, etc.).

## Column Operations

### Get Column Names

```python
df.columns  # Returns Index(['A', 'B', 'C', ...], dtype='str')
```

## Unique Values

### Finding Unique Values

```python
df["A"].unique()  # Returns array of unique values

df["A"].nunique()  # Returns count of unique values (int)
```

### Value Counts

```python
df["A"].value_counts()        # Returns Series (index: unique values, values: counts)
df["A"].value_counts().reset_index()  # Convert to DataFrame
```

## Creating New Columns

### Using `map()` and `apply()`

- **`map()`**: Element-wise operations on a Series. Supports dictionary lookups.
- **`apply()`**: More general; works on Series (element-wise) or DataFrames (row-wise with `axis=1`).

```python
# map: Simple transformations on Series
df["as_str"] = df["E"].astype(str)
df["len"] = df["as_str"].map(len)

# apply: Row-level operations (requires axis=1)
def process_row(row: pd.Series) -> number:
    return sum([row[letter] for letter in "A B C D E".split()])

df["sum"] = df.apply(process_row, axis=1)
```

**When to use each:**
| Use `map()` | Use `apply()` |
|------------|-------------|
| Simple type conversions | Complex operations needing multiple columns |
| Dictionary lookups | Row-wise operations |
| Speed is important | DataFrame transformations |

## Sorting

### Sort by Values

```python
df.sort_values(by="column_name", ascending=True/False)
```

## Null Values

### Finding Nulls

```python
df.isnull()  # Returns DataFrame of booleans (boolean mask)
```

### Related Methods
- `df.notnull()`: Inverse of `isnull()`
- `df.dropna()`: Remove rows with nulls
- `df.fillna(value)`: Fill nulls with value

## Pivot Tables

Create summarized views similar to Excel pivot tables.

```python
pd.pivot_table(
    df,
    index=["column1", "column2"],  # Rows (can be multi-level)
    columns="column3",            # Columns
    values="value_column"       # Values to aggregate
)
```

**Default aggregation is mean** — use `aggfunc` to specify different functions.

```python
pd.pivot_table(df, index="A", columns="B", values="C", aggfunc="sum")
```

## Additional Operations

### Shape and Info

```python
df.shape        # Returns (rows, columns) tuple
df.info()      # DataFrame summary (types, non-null counts)
df.describe() # Statistical summary for numeric columns
```

### Memory Usage

```python
df.memory_usage(deep=True)  # Memory usage in bytes per column
```