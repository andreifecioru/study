# Pandas Data Sanitization

**Date:** 2026-05-01
**Context:** Essential APIs for cleaning and normalizing messy datasets in Pandas.

---

## 1. Handling Duplicates
Redundant data is a common issue in raw exports.
- **`df.duplicated()`**: Identifies rows that have appeared before.
- **`df.drop_duplicates()`**: Removes them. Use `subset=['col']` to check specific columns.

## 2. String Cleaning
Text data often contains inconsistent whitespace or casing.
- **`df["col"].str.strip()`**: Removes leading/trailing spaces.
- **`df["col"].str.lower()` / `.str.upper()`**: Normalizes casing.
- **`df["col"].str.replace()`**: Performs regex-based text replacement.

## 3. Type Coercion
Numeric data imported as strings or containing "garbage" text (like "N/A" or "missing") requires coercion.
- **`pd.to_numeric(df['col'], errors='coerce')`**: The `coerce` flag is critical—it turns unparseable strings into `NaN` instead of raising an error.

## 4. Value Mapping & Replacement
- **`df.replace()`**: Replaces specific values across the whole DataFrame.
- **`df.map()`**: (Series only) Replaces values based on a dictionary mapping or a function.

## 5. Renaming
- **`df.rename()`**: Updates column names or index labels. Useful for converting "Dirty Column Name" to "snake_case".

---

**Tags:** #pandas #data-cleaning #sanitization #python
