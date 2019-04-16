#!/usr/bin/env python

if __name__ == '__main__':
  letters = list('abcde')
  numbers = range(0, 10)

  # cartesian product
  product = [(l, n) for l in letters
                    for n in numbers]

  for p in product:
    print(p)