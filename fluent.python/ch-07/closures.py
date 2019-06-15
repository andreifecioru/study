#!/usr/bin/env python

def make_average():
  sum = 0
  count = 0

  def average(v):
    # force the binding of sum and count as freevars
    nonlocal sum, count
    sum += v
    count +=1
    return sum/count

  return average


if __name__ == '__main__':
  avg = make_average()

  print(avg(10))
  print(avg(20))
  print(avg(30))
  print(avg(40))