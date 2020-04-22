'use strict';

function addTwo(input, delay) {
  return new Promise(function(resolve, reject) {
    setTimeout(() => {
      console.log(`Resolving promise after ${delay}ms`);
      resolve(input + 2);
    }, delay);
  });
}


// Promise
//   .resolve(3) // start with an initial value
//   .then(value => addTwo(value, 1000)) // this promise is rejected
//   .then(result => console.log(`Result: ${result}`)) // this is skipped
//   .catch(error => console.error(error)); // this is executed

// start with an initial promise
const startValue = Promise.resolve(3);

// create a list of promises
const promises = [
  startValue.then(value => addTwo(value, 100)),
  startValue.then(value => addTwo(value, 200)),
  startValue.then(value => addTwo(value, 50)),
];

// race: the first promise that gets resolved
Promise
  .race(promises)
  .then(result => console.log(`Result is: ${result}`));

// Output:
// Resolving promise after 50ms
// Result is: 5
// Resolving promise after 100ms
// Resolving promise after 200m

// all: gather results from all promises
Promise
  .all(promises)
  .then(result => console.log(`Result is: ${result}`)); // prints: Result is: 5,5,5