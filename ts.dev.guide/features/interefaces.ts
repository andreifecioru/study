const oldCivic = {
  name: 'civic',
  year: 2000,
  broken: true,
  summary(): string {
    return `[oldCivic] Name ${this.name}`;
  }
};

// long type annotations are very cumbersome
const printVehicle = (vehicle: {name: string; year: number; broken: boolean}): void => {
  console.log(`Name: ${vehicle.name}`);
  console.log(`Year: ${vehicle.year}`);
  console.log(`Broken: ${vehicle.broken}`);
};

printVehicle(oldCivic);

// interfaces introduce new types
interface Vehicle {
  name: string;
  year: number;
  broken: boolean;
}

// TS checks that the argument fits the "shape" of Vehicle interface (structural type system)
const printVehicle2 = (vehicle: Vehicle): void => {
  console.log(`\nName: ${vehicle.name}`);
  console.log(`Year: ${vehicle.year}`);
  console.log(`Broken: ${vehicle.broken}`);
};

printVehicle2(oldCivic);

interface Reportable {
  // abstract methods on interfaces (just the signature)
  summary(): string
}

const printReport = (item: Reportable): void => {
  console.log(`\n${item.summary()}`);
};

printReport(oldCivic);

// ---------------

const drPepper = {
  color: 'brown',
  carbonated: true,
  sugar: 40,

  summary(): string {
    return `[drPepper] This ${this.color} colored drink has ${this.sugar}g of sugar.`;
  }
};
printReport(drPepper);