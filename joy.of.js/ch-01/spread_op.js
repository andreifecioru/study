'user strict';

const arr1 = ['a', 'b', 'c'];
const arr2 = ['x', 'y', 'z'];

// concatenation
const result1 = [...arr1, ...arr2];
const result2 = [...arr1, 'another', ...arr2];

// object copy and selective property modification
const andrei = { name: 'Andrei', age: 39 }
const radu = {...andrei, name: 'Radu'};

console.log(andrei);
console.log(radu);