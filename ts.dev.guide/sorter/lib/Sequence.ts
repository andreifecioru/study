import {Comparable} from "./Comparable";

abstract class Sequence<T extends Comparable<T>> {
  abstract size(): number;

  abstract getAt(index: number): T;

  abstract push(value: T): void;

  abstract unshift(value: T): void;

  abstract asArray(): T[];

  sort(): Sequence<T> {
    if (this.isEmpty()) {
      return this.create();
    }

    const sorted = this.create();
    sorted.push(this.first());

    for (let i = 1; i < this.size(); i++) {
      const value = this.getAt(i);

      if (value.compare(sorted.first()) <= 0) {
        sorted.unshift(value);
      } else if (value.compare(sorted.last()) >= 0) {
        sorted.push(value);
      }
    }

    return sorted;
  }

  min(): T {
    if (this.isEmpty()) {
      throw new Error('Cannot get min of empty sequence');
    }

    let result = this.getAt(0);

    for (let i = 0; i < this.size(); i++) {
      if (result.compare(this.getAt(i)) > 0) {
        result = this.getAt(i);
      }
    }

    return result;
  }

  isEmpty(): boolean {
    return this.size() === 0;
  }

  copy(): Sequence<T> {
    const copy = this.create();

    for (let i = 0; i < this.size(); i++) {
      copy.push(this.getAt(i));
    }

    return copy;
  }

  first(): T {
    return this.getAt(0);
  }

  last(): T {
    return this.getAt(this.size() - 1);
  }

  protected abstract create(): Sequence<T>;
}

export {Sequence};