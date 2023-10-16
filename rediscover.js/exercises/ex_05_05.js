(function() {
  'use strict';

  const numbers = [1, 5, 2, 6, 8, 3, 4, 9, 7, 6];

  const sumOfDoubleOfEven = numbers
    .filter(n => n %2 === 0)
    .map(n => n * 2)
    .reduce((x, y) => x + y);

  console.log(`Result: ${sumOfDoubleOfEven}`);
})();