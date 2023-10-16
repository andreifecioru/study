(function() {
  'use strict';

  function greet(name, gender) {
    let designation = '';
    switch(gender) {
      case Symbol.for('female'):
        designation = 'Ms.';
        break;
      case Symbol.for('male'):
        designation = 'Mr.';
        break;
      default:
        throw new Error(`Unsupported gender: ${gender.toString()}`);
    }

    return `Hello, ${designation} ${name}`;
  }

  console.log(greet('Sara', Symbol.for('female')));
  console.log(greet('Tom', Symbol.for('male')));
  console.log(greet('Jim', Symbol.for('MALE')));
})();