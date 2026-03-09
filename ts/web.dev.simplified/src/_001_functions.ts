function _example_01(): void {
  function sum(a: number, b: number): number {
    return a + b;
  }

  console.log(`1 + 2 = ${sum(1, 2)}`);
}

// Excess property checking: TS checks for unknown properties only on object literals
// passed directly to a typed location. Assigning to a variable first bypasses the check
// because the object might be used elsewhere — TS can't know. Inline literals have no excuse.
function _example_02(): void {
  interface Person {
    name: string;
  }

  function showPerson(p: Person): void {
    console.log(p.name);
  }

  // this works
  const john = { name: "John", age: 28 };
  showPerson(john);

  // this does not work
  // showPerson({ name: "Jane", age: 44 });
}

function _example_03(): void {
  type Options = {
    debugMode?: boolean;
    logLevel?: number;
  };

  // default params and optional params
  function _showData_1(data: string = "no data", options?: Options): void {
    console.log(data, options);
  }

  // setting default values via destructuring
  // NOTE: setting a default value for a param, makes it optional
  function _showData_2(
    data: string = "no data",
    { debugMode = false, logLevel }: Options = {},
  ): void {
    console.log(data, debugMode, logLevel);
  }
}

function _example_04(): void {
  // rest param is an array
  const sum = (...nums: number[]): number => {
    return nums.reduce((acc, value) => acc + value);
  };

  console.log(`Sum is: ${sum(1, 2, 3, 4)}`);
}

function main(): void {
  _example_04();
}

export default main;
