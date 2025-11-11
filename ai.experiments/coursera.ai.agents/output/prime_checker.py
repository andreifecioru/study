import unittest

def is_prime(number: int) -> bool:
    """
    Checks if a given integer is a prime number.

    A prime number is a natural number greater than 1 that has no positive
    divisors other than 1 and itself.

    Edge Cases Considered:
    - Numbers less than 2 are not prime.
    - 2 is the only even prime number.
    - Negative numbers are not prime.

    Args:
        number: The integer to check for primality.

    Returns:
        True if the number is prime, False otherwise.

    Examples:
        >>> is_prime(2)
        True
        >>> is_prime(3)
        True
        >>> is_prime(4)
        False
        >>> is_prime(17)
        True
        >>> is_prime(1)
        False
        >>> is_prime(0)
        False
        >>> is_prime(-5)
        False
    """
    # Numbers less than 2 are not prime.
    if number < 2:
        return False
    # 2 is the only even prime number.
    if number == 2:
        return True
    # All other even numbers are not prime.
    if number % 2 == 0:
        return False
    # Check for divisibility from 3 up to the square root of the number,
    # incrementing by 2 to only check odd divisors.
    for i in range(3, int(number**0.5) + 1, 2):
        if number % i == 0:
            return False
    # If no divisors are found, the number is prime.
    return True

class TestIsPrime(unittest.TestCase):

    def test_prime_numbers(self):
        self.assertTrue(is_prime(2))
        self.assertTrue(is_prime(3))
        self.assertTrue(is_prime(5))
        self.assertTrue(is_prime(7))
        self.assertTrue(is_prime(11))
        self.assertTrue(is_prime(13))
        self.assertTrue(is_prime(17))
        self.assertTrue(is_prime(19))
        self.assertTrue(is_prime(97))

    def test_composite_numbers(self):
        self.assertFalse(is_prime(4))
        self.assertFalse(is_prime(6))
        self.assertFalse(is_prime(8))
        self.assertFalse(is_prime(9))
        self.assertFalse(is_prime(10))
        self.assertFalse(is_prime(12))
        self.assertFalse(is_prime(15))
        self.assertFalse(is_prime(100))

    def test_edge_cases(self):
        self.assertFalse(is_prime(0))
        self.assertFalse(is_prime(1))
        self.assertFalse(is_prime(-1))
        self.assertFalse(is_prime(-10))

    def test_large_prime(self):
        self.assertTrue(is_prime(7919)) # A known large prime

    def test_large_composite(self):
        self.assertFalse(is_prime(7921)) # 89 * 89

if __name__ == '__main__':
    unittest.main(argv=['first-arg-is-ignored'], exit=False)