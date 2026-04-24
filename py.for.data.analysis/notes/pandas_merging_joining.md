# Pandas Merging, Joining, and Column Reordering

**Date:** 2026-05-02
**Context:** Summary of DataFrame concatenation, merging, joining operations, and how to reorder columns after a merge.

---

## Concatenation (`pd.concat`)

### Row-wise (default)
Stacks DataFrames vertically — columns must match.

```python
left_df = pd.DataFrame(data=np.arange(1, 10).reshape(3,3), columns=["A", "B", "C"])
right_df = pd.DataFrame(data=np.arange(11, 20).reshape(3,3), columns=["A", "B", "C"])

result = pd.concat([left_df, right_df])
# Index is preserved from both frames (can be reset via .reset_index(drop=True))
```

### Column-wise (`axis=1`)
Stitches DataFrames horizontally — row count must match.

```python
left_df = pd.DataFrame(data=np.arange(1, 10).reshape(3,3), columns=["A", "B", "C"])
right_df = pd.DataFrame(data=np.arange(11, 20).reshape(3,3), columns=["D", "E", "F"])

result = pd.concat([left_df, right_df], axis=1)
```

---

## Merging (`df.merge`)

Similar to SQL table joins. You provide the key column via `on=`. Supports `how`: `inner` (default), `outer`, `left`, `right`.

```python
left_df = pd.DataFrame(data=np.arange(1, 10).reshape(3,3), columns=["A", "B", "C"])
left_df["key"] = ["k0", "k1", "k2"]

right_df = pd.DataFrame(data=np.arange(11, 20).reshape(3,3), columns=["D", "E", "F"])
right_df["key"] = ["k0", "k1", "k2"]

result = left_df.merge(right_df, how="inner", on=["key"])
# Columns: A, B, C, key, D, E, F  -- key ends up in the middle
```

---

## Joining (`df.join`)

Shorthand for `merge` when the join key is the DataFrame **index**.

```python
left_df = pd.DataFrame(
    data=np.arange(1, 10).reshape(3, 3),
    columns=["A", "B", "C"],
    index=["k0", "k1", "k2"],
)

right_df = pd.DataFrame(
    data=np.arange(11, 20).reshape(3, 3),
    columns=["D", "E", "F"],
    index=["k0", "k1", "k2"],
)

result = left_df.join(right_df, how="inner")
```

---

## Column Reordering

After a merge, the join key may not be where you want it. To reorder columns without changing the index (or copying data — this returns a view):

```python
# Move "key" to the front
result = result[["key", "A", "B", "C", "D", "E", "F"]]
```

General approach for dynamic reordering:

```python
cols = ["key"] + [col for col in df.columns if col != "key"]
df = df[cols]
```

---

**Tags:** #pandas #data-analysis #merge #join #concat #dataframe