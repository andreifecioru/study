const drink = {
  color: 'brown',
  carbonated: true,
  sugar: 40
};

// we can represent a 'record' of drink with tuples
// this specific type annotation transforms the array on the rhs into a tuple
const pepsi: [string, boolean, number] = ['brown', true, 40];

// in this scenarios we usually use type aliases
type Drink = [string, boolean, number];

const sprite: Drink = ['clear', true, 42];
const tea: Drink = ['brown', false, 0];