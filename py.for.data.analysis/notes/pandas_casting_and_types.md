# Pandas Type Casting and Nullable Types

## The Mental Model: Eager and Immutable
In pandas, casting operations (changing the data type of a column) almost always follow a **"Copy-and-Convert"** pattern.

### 1. The `astype()` Method
The primary way to change types is `.astype()`. It returns a **new** Series or DataFrame; it does not modify the original object in place.

```python
# Create a new Series with the converted type
s_int = s.astype('int32')

# Modify a DataFrame (returns a NEW DataFrame)
df = df.astype({'column_a': 'float32', 'column_b': 'int16'})
```

*   **Note on `inplace`:** The `inplace=True` parameter is being deprecated across pandas. Always prefer explicit reassignment: `df['A'] = df['A'].astype(int)`.

---

## The "NaN to Float" Promotion Rule
Historically, NumPy (and thus pandas) could not store a `NaN` (Not a Number) in a standard integer array. 

- **The Rule:** If an integer column encounters a `NaN` (even during an operation like `s1 + s2`), the entire column is promoted to `float64` to accommodate the missing value.
- **Result:** `1 (int) + NaN (float) = 1.0 (float)`.

### The Modern Fix: Nullable Integer Types
Pandas 1.0+ introduced **Extension Dtypes** that use a separate boolean mask to track missing values, allowing integers to remain integers even with `NaN`.

| NumPy/Legacy Type | Pandas Nullable Type |
| :--- | :--- |
| `int64` (no NaNs allowed) | `Int64` (Capital 'I', supports `NA`) |
| `bool` (no NaNs allowed) | `boolean` (Supports `NA`) |

```python
# Converting to a nullable integer
s = s.astype('Int64') 
```

---

## Robust Conversion: `pd.to_numeric()`
When dealing with "dirty" data (e.g., strings loaded from a CSV that contain non-numeric characters), `.astype()` will crash. Use `pd.to_numeric()` instead.

- **`errors='raise'`:** (Default) Crashes on invalid strings.
- **`errors='coerce'`:** Turns invalid strings (e.g., `"?"`, `"None"`) into `NaN`.
- **`errors='ignore'`:** Returns the original input if it can't be converted.

```python
# Safe conversion of a messy string column
df['price'] = pd.to_numeric(df['price'], errors='coerce')
```

---

## Memory Optimization: Downcasting
For large-scale data (where RAM is tight), you can downcast to smaller bit-widths.

- **Numeric:** `pd.to_numeric(s, downcast='integer')` will pick the smallest possible type (`int8`, `int16`, etc.) that can fit the data.
- **Categoricals:** For repetitive strings (e.g., "Male", "Female", "Other"), use the `category` dtype. This stores strings as integers internally, significantly reducing memory usage.

```python
df['gender'] = df['gender'].astype('category')
```
