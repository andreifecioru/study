const profile = {
  firstName: 'alex',
  age: 20,
  coords: {
    lat: 0,
    lng: 15
  },

  setAge(age: number): void {
    this.age = age;
  } 
};

// ES2015-like destructuring: the type annotation declares the *expected* structure of the rhs.
const { age }: {age: number} = profile;
const { firstName, coords: {lat, lng} }: {firstName: string, coords: {lat: number; lng: number}} = profile;