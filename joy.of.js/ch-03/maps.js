'use strict';

// initial values are passed as a list of k/v pairs
const persons = new Map([
  ['Maria', 39],
  ['Andrei', 39],
  ['Alex', 5]
]);
console.log(persons); // Map { 'Maria' => 39, 'Andrei' => 39, 'Alex' => 5 

// basic iteration via `entries()` method
for (const [name, age] of persons.entries()) {
  console.log(`${name} is ${age} years old.`);
}

// or via the `forEach` method
// NOTE: order of params is (value, key) => ...
persons.forEach((age, name) => console.log(`${name} is ${age} years old.`));

// access the set of keys
console.log(persons.keys());

// ... or the array of values
console.log(persons.values());

// set the value of a key (it adds the k/v pair if key is not present)
persons.set('Radu', 20);
console.log(persons);

// does the key exist in the map?
console.log(persons.has('Radu')); // true
