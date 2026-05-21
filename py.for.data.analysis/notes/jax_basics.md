# JAX Basics

JAX is a Python library for numerical computing that feels similar to NumPy, but adds three especially important capabilities:

- automatic differentiation
- just-in-time compilation
- vectorized function transforms

That is why JAX appears in the modern Python ML ecosystem alongside `NumPy`, `pandas`, `scikit-learn`, `PyTorch`, and `TensorFlow`.

## Where JAX Fits

A useful mental model is:

- `NumPy`: arrays and numerical operations
- `pandas`: tabular data work
- `scikit-learn`: ready-made classical ML algorithms
- `PyTorch` and `TensorFlow`: deep learning frameworks
- `JAX`: differentiable, compilable array programming for ML and scientific computing

JAX is lower-level than `scikit-learn`.
It is often more "math-first" than `scikit-learn`, and more function-transform oriented than typical `PyTorch` beginner usage.

## Core Idea

In JAX, you usually write ordinary mathematical functions and then apply transformations to them.

Instead of only saying:

```python
y = f(x)
```

you can also say:

- give me the derivative of `f`
- compile `f` so it runs faster
- apply `f` to a whole batch automatically

This is the main reason JAX is attractive for machine learning, optimization, and scientific computing.

## 1. `autodiff`

`autodiff` means automatic differentiation.

In ML, we often define:
- a model
- a loss function
- parameters we want to optimize

To improve the parameters, we need gradients.
A gradient tells us how the output changes when we slightly change the inputs or parameters.

JAX can compute these derivatives automatically.

Example:

```python
import jax.numpy as jnp
from jax import grad

def f(x):
    return x**2 + 3 * x

df = grad(f)

df(2.0)
```

The derivative of `x**2 + 3x` is `2x + 3`, so at `x = 2`, the answer is `7`.

Plain-English intuition:
- the function tells you the height of the hill
- the gradient tells you the slope
- optimization uses that slope to decide which direction to move

Why this matters in ML:
- training a model usually means minimizing a loss
- minimizing a loss usually requires gradients
- JAX makes gradient computation part of the normal workflow

## 2. `jit`

`jit` means just-in-time compilation.

Normally, Python executes code step by step, which is convenient but not always fast.
JAX can take a numerical function and compile it into a more efficient form for CPU, GPU, or TPU execution.

Example:

```python
from jax import jit

@jit
def f(x):
    return jnp.sin(x) + x**2
```

Plain-English intuition:
- normal Python execution is like following a recipe line by line every time
- `jit` is like preparing an optimized machine for the whole recipe ahead of time

Why this matters:
- repeated numeric workloads can become much faster
- the gain is especially useful in training loops and large array computations

Important nuance:
- the first call may feel slower because compilation happens then
- later calls are often much faster

So `jit` often trades startup cost for better repeated performance.

## 3. `vmap`

`vmap` means vectorized map.

Suppose you write a function for one example:

```python
def f(x):
    return x**2 + 1
```

Now suppose you want to apply it to a whole batch of values.
You could write a loop, but JAX can transform the function so it works over a batch automatically.

Example:

```python
from jax import vmap

batched_f = vmap(f)
batched_f(jnp.array([1.0, 2.0, 3.0]))
```

Plain-English intuition:
- `f` knows how to process one item
- `vmap` lifts it to process many items in parallel-like array form

Why this matters:
- batching is everywhere in ML
- it avoids manual loops
- it often leads to clearer and faster code

## A Simple Mental Picture

You can think of these three tools like this:

- `grad`: "How does this function change?"
- `jit`: "Can you make this function run efficiently?"
- `vmap`: "Can you make this function work over a batch automatically?"

That combination is powerful because ML often needs all three:
- gradients for learning
- speed for repeated computation
- batching for working with many examples

## Why JAX Feels Different

JAX encourages a style based on pure functions.
That means functions are easier to transform when they:

- take inputs explicitly
- return outputs explicitly
- avoid hidden state

This is part of why JAX code can feel elegant, but also why it can feel more demanding at first than ordinary Python scripting.

## When JAX Is a Good Fit

JAX is especially useful for:

- custom machine learning models
- optimization-heavy problems
- scientific computing
- simulation
- probabilistic modeling
- research code that benefits from gradients and accelerator hardware

It is often not the first tool you reach for when:

- the task is simple tabular ML
- `pandas` and `scikit-learn` already solve the problem cleanly
- you want a high-level estimator API more than a numerical programming system

## Short Summary

JAX matters in the Python ML ecosystem because it combines:

- NumPy-like array programming
- automatic differentiation
- compilation for speed
- convenient batching transforms

So in one sentence:

JAX is a library for writing mathematical Python code that can be differentiated, compiled, and scaled efficiently for machine learning and scientific computing.
