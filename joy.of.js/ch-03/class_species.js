'use strict';

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  clone() {
    const constructor_ = Reflect.getPrototypeOf(this).constructor;
    const cls = constructor_[Symbol.species] || constructor_;

    return new cls(this.firstName, this.lastName);

  }
}

class Employee extends Person {
  // here, we choose the use the base-class type
  static get [Symbol.species]() {
    return Person;
  }
}

class Student extends Person {
  // here, we choose the use the derived-class type
  static get [Symbol.species]() {
    return Student;
  }
}

const andrei = new Employee('Andrei', 'Fecioru').clone();
console.log(andrei); // Person { firstName: 'Andrei', lastName: 'Fecioru' }

const radu = new Student('Radu', 'Popescu').clone();
console.log(radu); // Student { firstName: 'Radu', lastName: 'Popescu' }
