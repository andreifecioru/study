import { IList } from "./list";

class ArrayList<T> implements IList<T> {
  private readonly _values: T[] = [];

  at(index: number): T {
    if (index >= this._values.length) {
      throw new Error("Out of range");
    }

    return this._values[index];
  }

  append(value: T): void {
    this._values.push(value);
  }

  get values(): T[] {
    return [...this._values];
  }
}

let list: IList<number> = new ArrayList<number>();
list.append(10);
list.append(20);
list.append(30);
list.append(40);
list.append(50);

console.log(list.values);