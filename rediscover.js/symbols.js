(function(){
  const s0 = Symbol();
  console.log(`Symbol s0: ${s0.toString()}`);

  const s1 = Symbol('andrei');
  console.log(`Symbol: ${s1.toString()}`);

  let andrei = {
    name: 'Andrei',
    [Symbol.for('age')]: 42
  };
  console.log(`Name: ${andrei.name}`);
  console.log(`Age: ${andrei[Symbol.for('age')]}`);

  Object.getOwnPropertySymbols(andrei).forEach(s => console.log(`Symbol prop: ${s.toString()}`));

  const symProp = Object.getOwnPropertySymbols(andrei)[0];
  console.log(`Age: ${andrei[symProp]}`);
  

  console.log('\n------------------\n');

  // symbols used to emulate interfaces
  class EMail {
    constructor(subject, body) {
      this.subject = subject;
      this.body = body;
    }

    toString() { return this.subject; }

    // one of JS's well-known symbols
    [Symbol.search](value) {
      const token = this.subject.toLowerCase();
      console.log(`Searching for ${token} in ${value}`);
      return value.search(token);
    }
  }

  const email = new EMail(
    "Meeting notes",
    "No action items were decided upon during this meeting."
  );
  console.log(`Email: ${email}`);
  const emailSubjects = ['meeting notes', 'backlog grooming', 'deployment procedure'];
  emailSubjects.forEach(subject => {
    console.log(`Looking for ${email} in <${subject}>: ${subject.search(email) >= 0}`);
  });

})();