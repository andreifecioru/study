'use strict';

const firstName = 'Andrei';
const lastName = 'Fecioru';
const work = 'Work';

const andrei = {
  // short-hand prop definition
  firstName,
  lastName,

  // short-hand method definition
  fullName() {
    return `${this.firstName} ${this.lastName}`;
  },

  // computed method (evaluates to 'doWork')
  [`do${work}`]() {
    return `${this.fullName()} is doing a lot of work!`

  }, // trailing comma
}

console.log(andrei);
console.log(andrei.fullName());
console.log(andrei.doWork());