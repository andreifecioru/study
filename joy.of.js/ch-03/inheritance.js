'use strict';

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  get fullName() {
    return `${this.firstName} ${this.lastName}`;
  }
}

class Employee extends Person {
  constructor(firstName, lastName, salary) {
    // the invocation of the base class constructor is mandatory
    // *before* accessing `this`
    super(firstName, lastName);
    this.salary = salary;
  }

  // override a field just by creating a field with the same name
  get fullName() {
    // access the base class implementation with `super`
    return `Employee: ${super.fullName}`;
  }

  showYearlyIncome() {
    console.log(`${this.fullName} earns $${this.salary * 12} per year.`)
  }
}

const andrei = new Employee('Andrei', 'Fecioru', 100);
andrei.showYearlyIncome(); // Employee: Andrei Fecioru earns $1200 per year.