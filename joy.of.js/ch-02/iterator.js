'use strict';

class Range {
  constructor(low, high) {
    this.low = low;
    this.high = high;
  }

  // implement the `Symbol.iterator` contract
  [Symbol.iterator]() {
    let current = this.low - 1;

    // capture 'this'
    const self = this;

    return {
      next() {
        current++;
        return {
          // when should we stop
          done: current == self.high,
          // what value to yield
          value: current
        }
      }
    }
  }
}

const range = new Range(10, 20);
for (const i of range) {
  console.log(i);
}