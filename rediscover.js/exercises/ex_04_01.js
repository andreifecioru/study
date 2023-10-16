(function(){
  'use strict';

  const letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];

  console.log('\-------[ OUTPUT 1 ]-------');
  for(const [idx, letter] of letters.entries()) {
    if (idx % 3 === 0) {
      console.log(letter);
    }
  }

  console.log('\-------[ OUTPUT 2 ]-------');
  Array.from(letters.entries())
    .filter(([idx, letter]) => idx % 3 === 0)
    .map(([idx, letter]) => letter)
    .forEach(letter => console.log(letter))
})();