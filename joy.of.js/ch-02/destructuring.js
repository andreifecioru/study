'use strict';

const andrei = {
  firstName: 'Andrei',
  lastName: 'Fecioru',
  address: {
    street : 'Baltita',
  }
}

console.log(andrei);

const modifiedAndrei = {...andrei, age: 39, address: {street: 'Dr. Fermei'}};
console.log(modifiedAndrei);