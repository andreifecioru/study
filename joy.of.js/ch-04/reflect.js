'use strict';

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  greet(message) {
    return `${message}, ${this.firstName} ${this.lastName}!`
  }
}

const andrei = new Person('Andrei', 'Fecioru');

const greeting = Reflect.apply(andrei.greet, andrei, ['Hi']);
console.log(greeting); // Hi, Andrei Fecioru!


// props are available on the instance itself
console.log(Reflect.ownKeys(andrei)); //[ 'firstName', 'lastName' ]

// methods are available on the prototype
console.log(Reflect.ownKeys(Person.prototype)); // [ 'constructor', 'greet' ]

// NOTE: we can get a method handler either via an instance (like above)
// or via the class' prototype
const greeting1 = Reflect.apply(Person.prototype.greet, andrei, ['Hi']);
console.log(greeting1); // Hi, Andrei Fecioru!

console.log(Reflect.getPrototypeOf(andrei)); // Person {}

// change the prototype chain
Reflect.setPrototypeOf(andrei, {});
console.log(Reflect.getPrototypeOf(andrei)); // {}

// retrieve the value of a field
console.log(Reflect.get(andrei, 'firstName')); // Andrei

// set the value of a field
Reflect.set(andrei, 'lastName', 'Ionescu');
console.log(andrei); // { firstName: 'Andrei', lastName: 'Ionescu' }

