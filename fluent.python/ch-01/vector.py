#!/usr/bin/env python

from math import hypot


class Vector:
  def __init__(self, x, y):
    self.x = x
    self.y = y

  def __repr__(self):
    return 'Vector({},{})'.format(self.x, self.y)

  def __abs__(self):
    return hypot(self.x, self.y)

  # define the "truthy-ness" of custom types
  def __bool__(self):
    return bool(self.x or self.y)

  def __add__(self, other):
    return Vector(self.x + other.x, self.y + other.y)

  def __mul__(self, scalar):
    return Vector(self.x * scalar, self.y * scalar)


if __name__ == '__main__':
  v1 = Vector(3, 4)
  v2 = Vector(1, 2)
  v3 = Vector(0, 0)

  print('abs({}): '.format(v1), abs(v1))
  print('bool({}): '.format(v1), bool(v1))
  print('bool({}): '.format(v3), bool(v3))


  # You can add vectors like any other numeric types
  print('{} + {}: '.format(v1, v2), v1 + v2)

  # You can multiply them (with a scalar) too
  print('{} * 2: '.format(v1), v1 * 2)
