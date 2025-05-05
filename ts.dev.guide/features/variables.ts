// values
const apples: number = 5;

// variables
let num: number = 10;
num = 15;

let speed: string = 'fast';
let hasName: boolean = true;
let nothingMuch: null = null; // null as a type vs. null as a value
let unknown: undefined = undefined; // same for undefined

// built-in objects
const now: Date = new Date();

// Arrays
const colors: string[] = ['red', 'green', 'blue'];
const myNumbers: number[] = [1, 2, 3, 4];
const someBools: boolean[] = [true, true, false];

// Classes
class Car {}
const aCar: Car = new Car();

// Type annotations for object literals
const point: {x: number; y: number} = {
  x: 10,
  y: 20
};

// Function type annotations
const logNumber: (num: number) => void = (num: number) => {
  console.log(`Number is ${num}`)
};

// When to use type annotations

// 1) When we want to refine the return value of a function which returns the 'any' type
const json = '{"x": 10, "y": 20}';
const coords: {x: number; y: number}  = JSON.parse(json);
console.log(`x: ${coords.x} | y: ${coords.y}`);

// 2) When definition and initialization are separate
const words = ['red', 'green', 'blue'];
let foundGreen: boolean;
foundGreen = false;

for (let word of words) {
  if (word === 'green') {
    console.log('Found the green color.');
    foundGreen = true;
    break;
  } 
}
console.log(`Did we find green? ${foundGreen}`);

// 3) When type cannot be inferred correctly
// Example: if we find a number above zero, use it; otherwise, use the value 'false' (union type)
const numbers = [-10, -1, 12];
let numAboveZero: number | boolean = false;
for (let num of numbers) {
  if (num > 0) {
    numAboveZero = num;
  }
}
console.log(`Do we have a number abive zero? ${numAboveZero}`);
