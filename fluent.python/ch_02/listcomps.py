if __name__ == "__main__":
  letters = list("abcde")
  numbers = range(10)

  # cartesian product
  product = [(letter, number) for letter in letters for number in numbers]

  for entry in product:
    print(entry)
