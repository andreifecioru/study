# the equivalent of const (JS) or val (Scala)
from typing import Final


def main():
  const: Final = 10
  # const = 11 # this is flagged by the type checker
  print(const)

if __name__ == '__main__':
  main()