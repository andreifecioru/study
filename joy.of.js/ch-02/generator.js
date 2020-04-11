'use strict';

class Range {
  constructor(low, high) {
    this.low = low;
    this.high = high;
  }

  // the `*` marks this method to be a generator
  *[Symbol.iterator]() {
    for (let i = this.low; i < this.high; i++) {
      // generators use the `yield` keyword
      yield i;
    }
  }
}

const range = new Range(10, 20)
for (const i of range) {
  console.log(i);
}

class ConcatRange {
  constructor(r1, r2) {
    this.r1 = r1;
    this.r2 = r2;
  }

  *[Symbol.iterator]() {
    // one can yield another generator (it will produce one value at a time)
    yield* this.r1;

    // one can yield multiple generators (this is affectivelly a concatenation)
    yield* this.r2;
  } 
}

const concat_range = new ConcatRange(
  new Range(10, 20),
  new Range(100, 110)
);

for (const i of concat_range) {
  console.log(i);
}

function *int_range(low, high) {
  for (let i = low; i < high; i++) {
    yield i
  }
}

for (const i of int_range(10, 20)) {
  console.log(i);
}