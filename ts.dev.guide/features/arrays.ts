// Arrays contain multiple values of same type

const carMakers: string[] = ['ford', 'toyota', 'chevy'];

// NOTE: we do need explicit type annotation when we initialize with an emtpy array
const someNumbers: number[] = [];

const dates: Date[] = [new Date(), new Date()];

// Nested arrays
const carsByMake: string[][] = [
  ['f150'],
  ['corolla'],
  ['camaro']
];

// Type inference when extracting values
const car = carMakers[0]; // TS knows cat is of type string
const myCar = carMakers.pop(); // ... same as above

// Prevent adding incompatible values
// carMakers.push(10); // this does not compile

// FP methods are available on arrays
carMakers
  .map((carMaker: string): string => {
  return carMaker.toUpperCase();
  })
  .forEach((carMaker) => { // we can use type inference in this scenarios
    console.log(`Car maker: ${carMaker}`)
  });

// Arrays with more than one type (use union types)
const importantDates: (string | Date)[] = [new Date(), '2023-10-10'];

