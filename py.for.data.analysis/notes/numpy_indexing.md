# NumPy Array Indexing and Slicing

NumPy provides powerful ways to access and modify data within arrays, ranging from basic slicing to conditional filtering.

## 1. Basic Slicing
Slicing in NumPy works similarly to Python lists: `array[start:stop:step]`.

```python
import numpy as np
arr = np.arange(10)
print(arr[:5])   # Elements from index 0 to 4
print(arr[3:8])  # Elements from index 3 to 7
```

### Performance Note: Views vs. Copies
- **Slices are "Views":** For optimization, slicing a NumPy array does **not** copy the data. It creates a "view" of the original memory.
- **Side Effects:** Modifying a slice **modifies the original array**.
- **Explicit Copies:** To create a separate array, use the `.copy()` method: `arr_copy = arr[3:8].copy()`.

## 2. Broadcasting
NumPy allows you to assign a single value to an entire slice (or array).

```python
arr[0:5] = 100  # Sets the first 5 elements to 100
```
*Note: If done on a slice, this modifies the original array due to the "view" behavior.*

## 3. Multi-dimensional Indexing
For 2D arrays (matrices), you can use comma-separated indices or slices.

```python
arr_2d = np.array([[5, 10, 15], [20, 25, 30], [35, 40, 45]])

# Accessing a single element
print(arr_2d[0, 1])   # Row 0, Col 1 (10) - Preferred
print(arr_2d[0][1])   # Same as above (Double-bracket notation)

# Slicing rows and columns
print(arr_2d[:2])        # First two rows
print(arr_2d[:2, 1:])    # First two rows, columns from index 1 onwards
```

## 4. Conditional Selection (Boolean Masking)
One of NumPy's most powerful features is selecting data based on a condition.

```python
arr = np.arange(0, 11)

# 1. Generate a boolean array (mask)
mask = arr > 5  # [False, False, ..., True, True]

# 2. Use the mask to filter the array
print(arr[mask])       # Returns elements [6, 7, 8, 9, 10]
print(arr[arr > 5])    # Combined one-liner
```

This "masking" technique allows for extremely fast data filtering without explicit loops.
