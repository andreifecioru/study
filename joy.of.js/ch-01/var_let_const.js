'use strict';

console.log(i)

for(var i = 0; i < 10; i++) {
  console.log(i);
}

console.log(i);

const person = Object.freeze({ name: 'Andrei', age: 40 });
person.age = 41;
console.log(person)