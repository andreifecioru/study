(function() {
  this.stuff = 'stuff';
  const value = 'value';
  const self = this;

  // var scope rules change in anonymous functions vs. arroe functions
  setTimeout(function() {
  console.log('---------[ Anonymous function ]----------');

    console.log(`Dynamic scope for this: ${this.stuff}`);
    console.log(`Lexical scope for self: ${self.stuff}`);
    console.log(`Lexical scope for value: ${value}`);
  }, 100);


  setTimeout(() => {
    console.log('---------[ Arrow function ]----------');

    console.log(`Lexical scope for this: ${this.stuff}`);
    console.log(`Lexical scope for self: ${self.stuff}`);
    console.log(`Lexical scope for value: ${value}`);
  }, 100);

})();