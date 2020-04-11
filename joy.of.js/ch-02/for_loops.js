'use strict';

const names = ['Andrei', 'Radu', 'Dumitru', 'Maria'];

for (let i = 0; i < names.length; i++) {
  console.log(`Name: ${names[i]} at index: ${i}`);
}

// simple iteration
for (const name of names) {
  console.log(`Name: ${name}`);
}

// iteration with index
for (const [idx, name] of names.entries()) {
  console.log(`Name: ${name} at index: ${idx}`);
}
