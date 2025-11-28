// Adds a timestamp property with creation date
class Timestamped {
  timestamp = new Date();
}

// Adds activate/deactivate methods and an `isActive` flag
class Activable {
  isActive = false;

  activate() {
    this.isActive = true;
  }

  deactivate() {
    this.isActive = false;
  }
}

// 2. Define a base class that you want to augment.
class User {
  constructor(public name: string) {}
}

// 3. Use a helper function to apply the mixins.
// This function will copy the properties and methods from the mixins
// to the target class.
function applyMixins(derivedCtor: any, constructors: any[]) {
  constructors.forEach((baseCtor) => {
    Object.getOwnPropertyNames(baseCtor.prototype).forEach((name) => {
      Object.defineProperty(
        derivedCtor.prototype,
        name,
        Object.getOwnPropertyDescriptor(baseCtor.prototype, name) ||
          Object.create(null)
      );
    });
  });
}

// 4. Create your final class and apply the mixins.
// We use `implements` to tell TypeScript about the combined shape.
class SmartUser extends User implements Timestamped, Activable {
  // The `implements` keyword doesn't add any code, it's just for
  // the type-checker. We must declare the properties that will be
  // mixed in to satisfy the compiler.
  timestamp!: Date;
  isActive!: boolean;
  activate!: () => void;
  deactivate!: () => void;

  constructor(...args: any[]) {
    super(args[0]); // Pass the 'name' to the User constructor
  }
}

// Apply the mixin implementations to the SmartUser class
applyMixins(SmartUser, [Timestamped, Activable]);

// Now you can use the composed class
const smartUser = new SmartUser("Alex");

console.log(`User: ${smartUser.name}`);
console.log(`Created at: ${smartUser.timestamp.toLocaleString()}`);

console.log(`Is active initially: ${smartUser.isActive}`);
smartUser.activate();
console.log(`Is active after activation: ${smartUser.isActive}`);

export { SmartUser, User, Timestamped, Activable, applyMixins };