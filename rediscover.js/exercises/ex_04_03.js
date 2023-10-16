(function() {
  'use strict';

  class Message {
    constructor(text) {
      this.text = text;
    }

    [Symbol.replace](token, replaceWith) {
      return this.text.replace(token, replaceWith);
    }
  }

  const message = new Message('There are no stupid questions.');

  console.log('stupid'.replace(message, 's*****'));
  console.log(''.replace(message, 'Yes, '));
})();