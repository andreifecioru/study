function _example_01(): void {
  // single source of truth for both run-time and compile time
  // "as const" is important: it freezes the array into a tuple with literal types
  const SKILL_LEVEL = ["Beginner", "Intermediate", "Expert"] as const;

  type _Person = {
    skillLevel: (typeof SKILL_LEVEL)[number];
  };

  SKILL_LEVEL.forEach((skill) => {
    console.log(skill);
  });
}

function _example_02(): void {
  interface User {
    name: string;
    age: number;
  }

  interface APIResponse<T extends object = Record<string, unknown>> {
    data: T;
    isError: boolean;
  }

  type UserResponse = APIResponse<User>;

  const response: UserResponse = {
    data: { name: "John", age: 33 },
    isError: false,
  };

  console.log(`Response: ${JSON.stringify(response)}`);
}

function _example_03(): void {
  function arrayToObj<T>(arr: [string, T][]): Record<string, T> {
    const result: Record<string, T> = {};

    arr.forEach(([key, value]) => {
      result[key] = value;
    });

    return result;
  }

  const john: [string, string | number][] = [
    ["name", "John"],
    ["age", 30],
  ];

  console.log(`Object: ${JSON.stringify(arrayToObj(john))}`);
}

function _example_04(): void {
  function wait(duration: number): Promise<string> {
    return new Promise<string>((resolve) => {
      setTimeout(() => {
        resolve("Hi");
      }, duration);
    });
  }

  void wait(1000).then((result) => {
    console.log(`Result is: ${result}`);
  });
}

function main(): void {
  _example_04();
}

export default main;
