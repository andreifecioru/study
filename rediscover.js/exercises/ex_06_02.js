(function() {
  'use strict';

  const name = 'Jane';

  function stripMargin(texts, ...expressions) {
    const result = expressions
      .map( (e, idx) =>
        `${texts[idx]}${e.toString()}`
      )

    return `${result}${texts[texts.length - 1]}`
    .split('\n')
    .map(line => line.trim())
    .join('\n');
  }

  const processed = stripMargin` This is for
    ${name} and it needs to be
      delivered by December 24th.`;

  console.log(processed);
})();