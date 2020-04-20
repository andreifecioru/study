'use strict';

function create_class(...fields) {
  const cls = class {
    constructor(...values) {
      fields.forEach((field, idx) => {
        this[field] = values[idx];
      });
    }
  }

  return cls;
}

const Person = create_class('firstName', 'lastName');

const andrei = new Person('Andrei', 'Fecioru');

// Output: cls { firstName: 'Andrei', lastName: 'Fecioru' }
console.log(andrei);