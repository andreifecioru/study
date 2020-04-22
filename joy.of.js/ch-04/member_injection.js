'use strict';

class Person {
  constructor(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}

const andrei = new Person('Andrei', 'Fecioru'); // Person { firstName: 'Andrei', lastName: 'Fecioru' }
console.log(andrei);

// injecting members at the instance level
andrei.age = 39;
console.log(`Age is: ${andrei.age}`); // Age is: 39

// when injecting function `this` refers to the instance being injected
andrei.fullName = function() { return `${this.firstName} ${this.lastName}`; }
console.log(`Full name is: ${andrei.fullName()}`); // Full name is: Andrei Fecioru

const radu = new Person('Radu', 'Dumitru');

// injection in the class' prototype
Person.prototype.age = 20;
Person.prototype.fullName = function() { return `${this.firstName} ${this.lastName}`; }

// newly injected members are available
// on instances that are created _before_ the injection site
console.log(`Age is: ${radu.age}`); // Age is: 20
console.log(`Full name is: ${radu.fullName()}`); // Full name is: Radu Dumitru

// prop injection at the instance level (getter)
const maria = new Person('Maria', 'Popescu');
Object.defineProperty(andrei, 'age', {
  get: function() {
    return 25;
  }
})

console.log(`Age is: ${maria.age}`); // Age is: 25

const dan = new Person('Dan', 'Ionescu');

// prop. injection at the class type (getter and setter)
// use `Object.defineProperties()` when defining multiple props at the same time
Object.defineProperties(Person.prototype, {
  age: {
    get: function() { return this._age || 0; },
    set: function(value) { this._age = value; },
  }
});

// static props are injected on the class itself.
Object.defineProperties(Person, {
  GENDERS: {
    get: function() { return ['MALE', 'FEMALE']; }
  }
});

dan.age = 30;
console.log(`Dan's age is: ${dan.age}`); // Dan's age is: 30
console.log(`Genders are: ${Person.GENDERS}`); // Genders are: MALE,FEMALE