# NumPy Array Basics

## 1. Creating Arrays
NumPy arrays (`ndarray`) are the core data structure for data science in Python. They are homogeneous (all elements must be of the same type).

### `np.arange([start, ]stop, [step, ]dtype=None)`
The NumPy equivalent of Python's `range()`, but returns an `ndarray`.
- **Exclusive Stop:** Like `range`, the `stop` value is not included.
- **Single Argument:** `np.arange(5)` -> `[0, 1, 2, 3, 4]`
- **Step Support:** `np.arange(0, 10, 2)` -> `[0, 2, 4, 6, 8]`
- **⚠️ Floating Point Warning:** `np.arange` supports float steps (e.g., `0.1`), but it can be unpredictable due to floating-point precision errors. It may occasionally include or skip the `stop` value depending on tiny rounding differences.

### `np.linspace(start, stop, num)`
Returns `num` evenly spaced samples over the interval. 
- **Inclusive:** By default, includes the `stop` value.
- **Preferred for Floats:** Much more reliable than `arange` when you need a specific number of decimal steps.

### Other Creation Methods
- **`np.array(list)`**: Converts a Python list to an array.
- **`np.zeros(shape)` / `np.ones(shape)`**: Quick shortcuts for arrays of 0s or 1s.
- **`np.full(shape, fill_value)`**: Creates an array pre-filled with a constant value.
- **`np.full_like(arr, fill_value)`**: Creates a new array with the same shape/type as `arr`, filled with a constant.
- **`np.meshgrid(x, y)`**: Creates coordinate matrices from coordinate vectors. Useful for evaluating functions over a grid.

## 2. Performance: Vectorization
NumPy operations are "vectorized," meaning operations are applied to the entire array at once via optimized C code, avoiding slow Python loops.

**Example Comparison:**
- **Python List:** `[x * 2 for x in my_list]` (Slow)
- **NumPy Array:** `my_arr * 2` (Fast)

## 3. Basic Operations
- **Scalar Math:** `arr + 2`, `arr * 5`, `arr ** 2`.
- **Element-wise Math:** `arr1 + arr2`, `arr1 * arr2`.
- **Universal Functions (ufuncs):** `np.sqrt(arr)`, `np.exp(arr)`, `np.sin(arr)`.

## 4. Shapes and Reshaping
- **`.shape`**: Returns a tuple representing the dimensions (e.g., `(rows, cols)`).
- **`.reshape(rows, cols)`**: Returns a new array with a new shape. The total number of elements must remain the same.
- **`.T`**: Property representing the transposed array.

## 6. Random Distributions

NumPy provides powerful tools for generating random data under the `np.random` module.

- **`np.random.rand(n)`**: Generates `n` values from a **Uniform Distribution** between 0 and 1. Every value has an equal probability of being chosen.
- **`np.random.randn(n)`**: Generates `n` values from a **Standard Normal (Gaussian) Distribution** (Mean = 0, Standard Deviation = 1). The values follow the "bell curve."
- **`np.random.randint(low, high, size)`**: Generates random integers within a specific range.
- **`np.random.normal(mu, sigma, size)`**: Generates a normal distribution with a custom Mean (`mu`) and Standard Deviation (`sigma`).
