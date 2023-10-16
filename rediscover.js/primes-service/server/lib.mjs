function isPrime(value) {
  if (value < 2) return true;

  for(let i = 2; i < value; i++) {
    if (value % i === 0) return false;
  }

  return true;
}

export function allPrimesLessThan(value) {
  const primes = [];
  if (isNaN(value) || value < 0)
    throw new Error(`Need a positive integer value. Got: ${value}`);

  for (let i = 1; i < value; i++) {
    if (isPrime(i))
      primes.push(i);
  }

  return primes;
}

// tests
// console.log(isPrime(10));
// console.log(isPrime(11));

//console.log(allPrimesLessThan(35));