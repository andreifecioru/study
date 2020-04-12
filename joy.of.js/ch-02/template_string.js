'use strict';

let message = String.raw`This is a raw string: \n \b \t`;
console.log(message);

function maskExpressions(texts, ...expressions) {
  // start with the 1st element in `texts`
  let result = texts[0];

  // texts and expressions are interleaved
  for (let i = 0; i < expressions.length; i++) {
    const masked = '*'.repeat(expressions[i].toString().length);
    result = `${result}${masked}`;
  }

  // finish with the last element in `texts`
  return `${result}${texts[texts.length - 1]}`;
}

message = maskExpressions`5 squared is: ${5 * 5}`;
console.log(message); // prints '5 squared is **'