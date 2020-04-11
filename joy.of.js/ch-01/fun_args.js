'use strict';

function compute_max(...values) {
  // this prints "true"
  console.log(values instanceof Array);

  let ret_val = values[0];

  for(let i = 1; i < values.length; i++) {
    if (values[i] > ret_val) {
      ret_val = values[i];
    }
  }

  return ret_val;
}

const max_val = compute_max(3, 2, 7, 10, 1);
console.log(`Max value is ${max_val}`);

// using the spread operator
const values = [3, 2, 7, 10, 1];
console.log(`Max value is ${compute_max(...values)}`);

function print_names(firstName, lastName, ...rest) {
  console.log(`Full name: ${firstName} ${lastName}`);
  console.log(`... and the rest: ${rest}`);
}

const names = ['Andrei', 'Fecioru', 'other', 'names', 'follow'];
print_names(...names);

function display_person(name, age = 10, gender = 'm') {
  console.log(`Name: ${name}. Age: ${age}. Gender: ${gender}`);
}

display_person('Andrei', 39);
display_person('Radu');

// passing 'undefined' forces the use of the default value
display_person('Jane', undefined, 'f');

function compute_tax(
  amount, 
  stateTax=15, 
  // named params can be expressions 
  // (which may referece other named params to the left)
  localTax=stateTax *.1) {

  return amount * (stateTax + localTax);
}

console.log(`Tax is: ${compute_tax(1000, .5)}`);