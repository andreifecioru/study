import {Sequence} from "./Sequence";
import {Comparable} from "./Comparable";

class Item<T extends Comparable<T>> {
  readonly value: T;
  next: Item<T> | null = null;

  constructor(value: T) {
    this.value = value;
  }
}

class LinkedList<T extends Comparable<T>> extends Sequence<T> {
  private head: Item<T> | null = null;
  private tail: Item<T> | null = this.head;
  private _size: number = 0;

  override create(): LinkedList<T> {
    return new LinkedList<T>();
  }

  override push(value: T): void {
    const newItem = new Item(value);
    if (!this.tail) {
      this.head = newItem;
      this.tail = this.head;
    } else {
      this.tail.next = newItem;
    }

    this._size += 1
  }

  override unshift(value: T): void {
    const newItem = new Item(value);
    if (!this.head) {
      this.head = newItem;
      this.tail = this.head;
    } else {
      newItem.next = this.head;
      this.head = newItem;
    }

    this._size += 1
  }

  override size(): number {
    return this._size;
  }

  override getAt(index: number): T {
    if (this.isEmpty()) {
      throw new Error(`Index ${index} out of bounds`);
    }

    let cursor = this.head;
    for (let i = 0; i < index; i++) {
      cursor = cursor!.next;
    }

    if (!cursor) {
      throw new Error(`Index ${index} out of bounds`);
    }

    return cursor.value;
  }

  override asArray(): T[] {
    let output: T[] = [];
    let cursor = this.head;
    while (cursor) {
      output.push(cursor.value);
      cursor = cursor.next;
    }

    return output;
  }
}

export {LinkedList};