from math import hypot
from typing import Self


class Vector:
  def __init__(self, x: float, y: float) -> None:
    self.x = x
    self.y = y

  def __repr__(self) -> str:
    return f"Vector({self.x},{self.y})"

  def __abs__(self) -> float:
    return hypot(self.x, self.y)

  # define the "truthy-ness" of custom types
  def __bool__(self) -> bool:
    return bool(self.x or self.y)

  def __add__(self: Self, other: Self) -> Self:
    return type(self)(self.x + other.x, self.y + other.y)

  def __mul__(self: Self, scalar: float) -> Self:
    return type(self)(self.x * scalar, self.y * scalar)


if __name__ == "__main__":
  v1 = Vector(3, 4)
  v2 = Vector(1, 2)
  v3 = Vector(0, 0)

  print(f"abs({v1}): ", abs(v1))
  print(f"bool({v1}): ", bool(v1))
  print(f"bool({v3}): ", bool(v3))

  # You can add vectors like any other numeric types
  print(f"{v1} + {v2}: ", v1 + v2)

  # You can multiply them (with a scalar) too
  print(f"{v1} * 2: ", v1 * 2)
