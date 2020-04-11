'use strict';

const s0 = Symbol()
console.log(`Symbol: ${s0.toString()}`);

const s1 = Symbol('andrei');
const s2 = Symbol('andrei')
console.log(s1 == s2);
console.log(s1 === s2);

const salary = Symbol('salary');

const andrei = {
  name: 'Andrei',
  age: 39,
  // a symbol-based proerty (need to use brackets)
  [salary]: 100
};

// symbol-based props can be accessed via bracket notation
console.log(`Andrei's salary ${andrei[salary]}`);

// symbol-based props are hidden by for...in construct
for (const prop in andrei) {
  console.log(`${prop}: ${andrei[prop]}`);
}
console.log(`Prop. list: ${Object.getOwnPropertyNames(andrei)}`);

// .. but can be accessed via  Object#getOwnPropertySymbols()
console.log(`Symbol-based prop. list: ${Object.getOwnPropertySymbols(andrei).map(s => s.toString())}`);

// the symbol is created once and returned 
// for every invocation with the same key
const sym1 = Symbol.for('unique key');
const sym2 = Symbol.for('unique key');
console.log(sym1 == sym2);
console.log(sym1 === sym2);

console.log(Symbol.keyFor(s1));