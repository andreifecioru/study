'use strict';

function compute_square(num) {
  const square = (n) => {
    // both 'this' and 'arguments' are visible due to the lexical scope

    // 'this' is undefined (the outer function is not a constructor)
    console.log(`The value of 'this': ${this}`);
    // 'arguments' refers to the list of arguments of the outer function
    console.log(`The value of 'arguments': ${arguments}`);

    return n * n;
  }

  return square(num);
}

const result = compute_square(5);
console.log(`Result is: ${result}`);