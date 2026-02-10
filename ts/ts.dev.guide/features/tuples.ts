const drink = {
  color: 'brown',
  carbonated: true,
  sugar: 40
};

// we can represent a 'record' of drink with tuples
// this specific type annotation transforms the array on the rhs into a tuple
const pepsi: [string, boolean, number] = ['brown', true, 40];

// Tuples are arrays with a fixed number of elements
// and a fixed type for each element
// pepsi[0] = 42; // Error: Type 'number' is not assignable to type 'string'
pepsi[0] = 'brown'; // OK

// in this scenarios we usually use type aliases
type Drink = [string, boolean, number];

const sprite: Drink = ['clear', true, 42];
const tea: Drink = ['brown', false, 0];

// Useful wehn dealing with CSVs or other data structures that have a fixed number of elements
