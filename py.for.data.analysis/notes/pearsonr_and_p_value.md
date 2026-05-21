# Pearson `r` and `p` in Joint Plots

`pearsonr` and `p` answer two different questions about the relationship between the two variables.

In your case, the variables are `fare` and `age` (in reference to the `titanic` dataset built into `seaborn`).

## 1. `pearsonr`

This is the Pearson correlation coefficient, usually written as `r`.

It measures:
- direction of the linear relationship
- strength of the linear relationship

Its value is between `-1` and `1`.

How to interpret it:
- `r = 1`: perfect positive linear relationship
- `r = -1`: perfect negative linear relationship
- `r = 0`: no linear relationship

What the sign means:
- positive `r`: as one variable increases, the other tends to increase
- negative `r`: as one variable increases, the other tends to decrease

What the magnitude means:
- close to `0`: weak linear relationship
- closer to `1` or `-1`: stronger linear relationship

So if you get:

```python
pearsonr = 0.096
```

that means:
- the relationship is positive
- but extremely weak

In plain English:
- passengers with higher fares may be slightly older on average
- but the linear relationship is very small

## 2. `p`

This is the p-value associated with a hypothesis test for the correlation.

The test is usually:

- null hypothesis (`H0`): true correlation = 0
- alternative hypothesis (`H1`): true correlation is not 0

So the p-value tells you:
- if the true correlation were actually zero
- how surprising would it be to observe a sample correlation at least this extreme?

Small p-value:
- evidence against `H0`
- suggests the correlation is probably not zero

Large p-value:
- not enough evidence against `H0`
- observed correlation could easily be due to chance

Typical rule of thumb:
- `p < 0.05`: statistically significant
- `p >= 0.05`: not statistically significant

So if you get:

```python
p = 0.01
```

that means:
- this correlation is statistically significant at the 5% level
- even though the correlation is weak

## Important point

Statistical significance is not the same as practical importance.

You can have:
- tiny `r`
- but small `p`

This often happens when the dataset is fairly large.

That means:
- the relationship is probably real
- but it is still weak

So for:

```python
pearsonr = 0.096; p = 0.01
```

the correct reading is:

- there is evidence of a non-zero linear relationship
- but that relationship is very weak

## Very important distinction

- `r` tells you effect size / strength
- `p` tells you evidence against "no relationship"

So:
- `r` = "how big is the linear association?"
- `p` = "how confident are we that it is not just random noise?"

## In the Titanic example

If `fare` and `age` have:
- `r = 0.096`
- `p = 0.01`

that suggests:
- older passengers may have paid slightly more on average
- but age explains very little of fare variation
- the relationship is weak, though statistically detectable

## Common confusion: what the null hypothesis says

It is easy to mix up the roles of `r`, `p`, and the null hypothesis.

For a Pearson correlation test:
- `H0`: the true linear correlation is `0`
- `H1`: the true linear correlation is not `0`

So the null is not:
- "the correlation is weak"

It is:
- "there is no linear correlation in the population"

That means the two summaries play different roles:
- `r` tells you how strong the observed linear relationship is
- `p` tells you how compatible that observed result is with a true correlation of zero

So for a result like:

```python
r = 0.096
p = 0.01
```

the correct reading is:
- the observed linear relationship is weak
- but it is unlikely to be exactly zero

In short:
- small `r` means weak effect
- small `p` means evidence against zero effect

That is why a result can be:
- statistically significant
- but practically small

## Quick analogy

Imagine hearing a faint sound in a quiet room:
- `r` = how loud the sound is
- `p` = how sure you are that there really is a sound

You can be:
- very sure the sound exists
- while it is still very faint

That is exactly what "small `r`, small `p`" means.

## Additional explanation: what Pearson correlation misses

Pearson correlation captures linear relationship only.

That means it is good at detecting patterns like:

```text
as x increases, y tends to increase in a roughly straight-line way
```

or:

```text
as x increases, y tends to decrease in a roughly straight-line way
```

But it can miss strong non-linear relationships.

### Example: U-shaped pattern

Suppose:
- small values of `x` produce large values of `y`
- middle values of `x` produce small values of `y`
- large values of `x` produce large values of `y`

That is a strong relationship, but it is not linear. It is curved.

In a case like that, Pearson `r` can be close to `0` even though the variables are clearly related.

So:
- `r ≈ 0` does not always mean "no relationship"
- it may only mean "no linear relationship"

### Example intuition

Imagine plotting points in a perfect parabola:

```text
y = x^2
```

There is obviously a very strong relationship between `x` and `y`.

But because the pattern is symmetric and curved rather than straight, Pearson correlation may be low or even near zero.

### Why this matters

If you only look at `r`, you might conclude:

```text
there is basically no relationship here
```

when the scatterplot actually shows a strong curved pattern.

That is why the plot itself matters. Numbers help, but they do not replace visual inspection.

## Another analogy

Pearson correlation is like asking:

```text
How well could I describe this relationship with a straight line?
```

If the true pattern is curved, Pearson `r` may answer:

```text
Not very well
```

even when the variables are strongly connected.

## Practical takeaway

When you see Pearson `r`:
- use it as a measure of linear association
- do not treat it as a universal measure of all possible relationships
- always check the scatterplot too

That gives you both:
- a numeric summary
- and a visual sanity check

## Spearman correlation

Spearman correlation is a useful alternative when you care about monotonic relationship rather than strictly linear relationship.

It is usually written as Spearman's rho, often shown as `rho` or `r_s`.

### What it measures

Spearman correlation asks:

```text
As one variable goes up, does the other tend to go up or down consistently?
```

It works on ranks rather than raw values.

That means:
- it replaces the original values with their sorted positions
- then computes correlation on those ranks

Because of that, it is less sensitive to:
- non-linear but monotonic patterns
- extreme outliers

### Monotonic vs linear

A relationship is monotonic if:
- as `x` increases, `y` generally keeps increasing
- or as `x` increases, `y` generally keeps decreasing

But the increase does not have to follow a straight line.

So a relationship can be:
- monotonic but not linear

Example:
- `y = log(x)` is increasing, but curved
- Pearson may not capture it perfectly
- Spearman will usually capture the increasing trend better

### When Spearman is helpful

Use Spearman when:
- the scatterplot looks curved but still consistently increasing or decreasing
- you care about ordered relationship more than exact numeric spacing
- outliers may distort Pearson correlation

### Quick comparison

Pearson asks:

```text
How well does a straight line describe this relationship?
```

Spearman asks:

```text
Do higher values of one variable tend to come with higher values of the other?
```

### Example intuition

Suppose:
- passenger A paid less fare than passenger B
- passenger B paid less fare than passenger C

If ages also tend to increase in that same ordering, Spearman correlation will be positive, even if the exact shape is curved.

### Practical rule

- use Pearson for linear association
- use Spearman for monotonic association
- inspect the scatterplot before trusting either summary blindly

### In SciPy

Pearson correlation:

```python
from scipy import stats

r, p = stats.pearsonr(x, y)
```

Spearman correlation:

```python
from scipy import stats

rho, p = stats.spearmanr(x, y)
```

So if Pearson is small but the plot still shows a clear increasing or decreasing curved trend, Spearman is often the next thing to check.
