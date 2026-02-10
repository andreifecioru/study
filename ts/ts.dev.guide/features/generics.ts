class Optional<T> {
  static of<V>(value: V): Optional<V> {
    return new Optional(value);
  }

  static empty<V>(): Optional<V> {
    return new Optional(null as V);
  }

  private value: T|null = null;

  constructor (value: T|null) {
    this.value = value;
  }

  get isDefined(): boolean {
    return this.value != null;
  }

  get(): T {
    if (this.isDefined) return this.value as T;
    throw new Error('calling get() of empty Option');
  }

  getOrElse(value: T): T {
    if (this.isDefined) return this.value as T;
    return value;
  }
}

const maybeNum = Optional.of(5);
console.log(maybeNum.get());

const emptyNum = Optional.empty<number>();
console.log(emptyNum.getOrElse(10));

// ---- Generic constraints
interface Printable {
  print(): void;
}

class Person implements Printable {
  constructor(
    readonly firstName: string,
    readonly lastName: string
  ) {}

  print(): void {
    console.log(`${this.firstName} ${this.lastName}`);
  }
}

function print<T extends Printable>(arr: T[]): void {
  arr.forEach(element => {
    element.print();
  });
}

print([
  new Person('John', 'Doe'),
  new Person('Jane', 'Smith'),
])