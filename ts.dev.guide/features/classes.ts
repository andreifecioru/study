
class Vehicle {
  // NOTE: default access modifier is public

  // Fields
  maxSpeed: number = 180; // initialized fields

  // Constructor
  constructor(public color: string) { } // by adding an access modifier, color is now a field

  /* ... same as
  constructor(color: string) {
    this.color = color;
  }
  */

  // Methods
  public drive(): void {
    console.log('Driving along...');
  }

  protected honk(): void {
    console.log('Beeep');
  }
}

const aVehicle = new Vehicle('orange');
aVehicle.drive();
console.log(`Vehicle is of color: ${aVehicle.color}`);

class Car extends Vehicle {
  constructor(public horsePower: number, 
              color: string /* NOTE: we don't put an access modifier here 
                               because we don't want to create a new field. */) {
    // the constructor of the parent needs to be invoked
    super(color);
  }

  // override methods in the parent class
  drive(): void {
    this.startEngine();
    console.log(`Driving my ${this.color} car around with all my ${this.horsePower}HP.`)
  }

  private startEngine(): void {
    this.honk()
    console.log('Starting my engine.')
  }
}

const aCar = new Car(500, 'black');
aCar.drive();