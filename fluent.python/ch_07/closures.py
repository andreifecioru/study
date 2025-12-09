from collections.abc import Callable


def make_average() -> Callable[[float], float]:
  _sum = 0.0
  count = 0

  def average(v: float) -> float:
    # force the binding of sum and count as freevars
    nonlocal _sum, count
    _sum += v
    count += 1
    return _sum / count

  return average


if __name__ == "__main__":
  avg = make_average()

  print(avg(10))
  print(avg(20))
  print(avg(30))
  print(avg(40))
