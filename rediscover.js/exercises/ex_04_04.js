(function() {
  'use strict';

  function* fibSeries() {
    let v1 = 0
    let v2 = 1
    while (true) {
      const nextElem = v1 + v2;
      yield nextElem;
      v1 = v2;
      v2 = nextElem;
    }
  }

  for(const value of fibSeries()) {
    if (value > 25) break;
    process.stdout.write(`${value}, `);
  }
})();