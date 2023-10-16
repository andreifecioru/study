(function() {
  'use strict';

  const greet = (greeting, ...names) => {
    console.log(greeting + ' ' + names.join(', '));
  };

  const helloJackJill = greet.bind(null, 'hello', 'Jack', 'Jill');

  helloJackJill();


})();