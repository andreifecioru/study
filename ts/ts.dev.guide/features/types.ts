const today = new Date();

console.log(`Today is the ${today.getDay()} day of the week.`);

const person = {
  age: 20
};

console.log(`Person's age is: ${person.age}`);

class Color {}
const red = new Color();

// strings can be types (singleton types)
type Red = 'red';

function colorMeRed(color: Red) {
  console.log(`Coloring with ${color}`);
}

// we can only call the method with the string 'red'
colorMeRed('red');