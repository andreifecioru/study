interface Person {
  name: string;
  age: number;
}

function _example_01(): void {
  // keyof usage

  function getProp<K extends keyof Person>(key: K, person: Person): Person[K] {
    return person[key];
  }

  const john = { name: "John", age: 25 };

  console.log(`John's age: ${getProp("age", john)}`);
}

function _example_02(): void {
  const persons = [
    { name: "John", age: 25 },
    { name: "Jane", age: 30 },
    { name: "Andrew", age: 25 },
    { name: "Maria", age: 30 },
  ];

  function groupBy<K extends keyof Person>(
    key: K,
    persons: Person[],
  ): Map<Person[K], Person[]> {
    const result: Map<Person[K], Person[]> = new Map();

    persons.forEach((person) => {
      const keyVal = person[key];
      const existing = result.get(keyVal);

      if (existing !== undefined) {
        result.set(keyVal, [...existing, person]);
      } else {
        result.set(keyVal, [person]);
      }
    });

    return result;
  }

  console.log("Group by age:");
  groupBy("age", persons).forEach((group, age) => {
    console.log(`  + ${age}:`);
    group.forEach((person) => {
      console.log(`    - ${person.name}`);
    });
  });
}

function main(): void {
  _example_02();
}

export default main;
