'use strict';

class Counter{}
Counter.prototype.value = 0;
Counter.prototype.increment = function() { this.value ++; }
Counter.prototype.show = function() { console.log(`Count: ${this.value}`); }

const c1 = new Counter();
const c2 = new Counter();

// both queries for the `value` field search the inheritance tree
c1.show(); // Count: 0
c2.show(); // Count: 0

// a new `value` field is set on the `c1` object
c1.increment();

// gets the `value` set on `c1`
c1.show(); // Count: 1
// get the `value` set on the prototype
c2.show(); // Count: 0
