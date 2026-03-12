function _example_01(): void {
  type User = {
    id: string;
    firstName: string;
    lastName: string;
  };

  type NewUser = Omit<User, "id">;
  /* Same as:
    type NewUser = Pick<User, "firstName" | "lastName">;
  */

  function saveUser(newUser: NewUser): User {
    return { ...newUser, id: crypto.randomUUID() };
  }

  function renderUser(user: User): void {
    console.log(`User: ${JSON.stringify(user)}`);
  }

  const savedUser = saveUser({ firstName: "John", lastName: "Doe" });
  renderUser(savedUser);
}

function _example_02(): void {
  type User = {
    id: number;
    username: string;
    email?: string;
  };

  // NOTE: when intersecting, required keys override optional keys
  type RequiredPick<T, K extends keyof T> = Required<Pick<T, K>> & T;
  type PartialPick<T, K extends keyof T> = Partial<Pick<T, K>> &
    Required<Omit<T, K>>;

  type _UserWithEmail = RequiredPick<User, "email">;
  type _UserWithoutName = PartialPick<User, "username">;
}

function main(): void {
  _example_01();
}

export default main;
