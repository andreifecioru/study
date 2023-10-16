(function() {
  'use strict';

  function* fibSeries() {
    let v1 = 0
    let v2 = 1
    let index = 0;

    while (true) {
      const nextElem = v1 + v2;
      yield [index, nextElem];
      index ++;
      v1 = v2;
      v2 = nextElem;
    }
  }

  for(const [index, value] of fibSeries()) {
    if (index > 8) break;
    process.stdout.write(`${value}, `);
  }
})();