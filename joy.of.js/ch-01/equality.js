const a = '1';
const b = 1;
const c = '1.0';

// type-coercion is applied before equality is checked
console.log(a == b);
console.log(b == c);
console.log(a == c);

// no type-coercion is applied
console.log(a === b)