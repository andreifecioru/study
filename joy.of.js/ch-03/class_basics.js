'use strict';

const GET_FULL_NAME = 'get_full_name';

class Person {
  // special constructor method
  constructor(firstName, lastName, age = 0, gender = Person.MALE) {
    this.firstName = firstName;
    this.lastName = lastName;
    this._age = age;
    this.gender = gender;
  }

  show() {
    // access iVars only via `this`
    console.log(`Person: ${this.firstName} ${this.lastName} is ${this.gender} (age: ${this.age})`);
  }

  // computed members
  [GET_FULL_NAME]() {
    return `${this.firstName} ${this.lastName}`;
  }

  // getter prop
  get age() {
    return this._age;
  }

  // setter prop
  set age(value) {
    this._age = value;
  }

  // static getters
  static get genders() {
    // here, `this` is dynamically typed: most of the time `this` refers to the class
    // but to be extra safe, we always access static fields explicitly via the class name.
    return [Person.MALE, Person.FEMALE];
  }

  // static methods
  static pickOlder(p1, p2) {
    return p1.age >= p2.age ? p1 : p2;
  }
}

// static fields are introduced outside class definition
Person.MALE = 'male';
Person.FEMALE = 'female';

const andrei = new Person("Andrei", "Fecioru");
andrei.show();

// access computed members
console.log(`Andrei's full name is: ${andrei[GET_FULL_NAME]()}`);

// when reading a prop don't use parens
console.log(`Andrei's age is: ${andrei.age}`);

// setting a prop
andrei.age = 39;
console.log(`Andrei's age is: ${andrei.age}`);
