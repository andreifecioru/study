'use strict';

// the contract is defined by a set of symbols
const fullName = Symbol('fullName');

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  // implement the 'fullName' contract
  [fullName]() {
    return `${this.firstName} ${this.lastName}`;
  }
}

const andrei = new Person('Andrei', 'Fecioru');

// we expect Person to implement the 'fullName' contract
console.log(`Full name: ${andrei[fullName]()}`);