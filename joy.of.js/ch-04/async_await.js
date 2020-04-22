'use strict';

function addTwo(input) {
  return new Promise(function(resolve, reject) {
    if (input % 2 === 0) {
      setTimeout(() => resolve(input + 2), 1000);
    } else {
      reject('Only even numbers are supported. Got: ${input}.')
    }
  });
}

// await can only be used inside functions marked as async
async function addTwoToList(input) { // add two to all elements in list
  try {
    return await Promise.all(input.map(value => addTwo(value)));
  } catch (ex) { // in case of error resolve with an empty list
    console.error(ex);
    return Promise.resolve([]);
  }
}

// all elems are even; prints out: Result is: [12,22,32]
addTwoToList([10, 20, 30])
  .then(result => console.log(`Result is: [${result}]`))

// one of the elements is odd; prints out: Result is: []
addTwoToList([10, 21, 30])
  .then(result => console.log(`Result is: [${result}]`))
