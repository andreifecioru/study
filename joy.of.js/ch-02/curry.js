'use strict';

function add(op1, op2) {
  return op1 + op2;
}

const addThree = add.bind(null, 3);

console.log(addThree(5)); // prints '8'