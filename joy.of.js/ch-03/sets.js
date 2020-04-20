'use strict';

// create a set out of list -> duplicates removed
const names = new Set(['Andrei', 'Maria', 'Andrei']);
console.log(names); // Set { 'Andrei', 'Maria' }

// `size` is a prop
console.log(`Set size is: ${names.size}`);

// add elements to set - supports method chaining
names.add('Alex').add('Maria').add('Radu'); // Set { 'Andrei', 'Maria', 'Alex', 'Radu' }
console.log(names);

// basic iteration
names.forEach(name => console.log(name));

// Set has no implementation for functions like `filter` and `map`
// You need to cast to an array for this
const processed = [...names] // array-casting via spread operator
  .filter(name => name.startsWith('A'))
  .map(name => name.toLowerCase());

//... you can always go back to a Set of you need to
const result = new Set(processed);
console.log(result); // Set { 'andrei', 'alex' }

// test presence of key in set
console.log(names.has('Andrei')); // true
