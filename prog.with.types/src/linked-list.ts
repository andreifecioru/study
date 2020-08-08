import { IList } from "./list";

interface IListNode<T> {
  readonly isEmpty: boolean;
  readonly value: T;
  next: IListNode<T>;
}

class EmptyListNode implements IListNode<never> {
  readonly isEmpty = true;
  private constructor() {}
  private static _instance: EmptyListNode = undefined;
  static get instance(): EmptyListNode {
    if (!EmptyListNode._instance) {
      EmptyListNode._instance = new EmptyListNode();
    }
    return EmptyListNode._instance;
  }
  get value(): never { throw new Error("Empty node"); }
  get next(): IListNode<never> { throw new Error("Empty node"); }
  set next(node: IListNode<never>) { throw new Error("Empty node"); }
}

class ListNode<T> implements IListNode<T> {
  private readonly _value: T;
  private _next: IListNode<T>;

  readonly isEmpty = false;

  constructor(value: T) {
    this._value = value;
    this._next = EmptyListNode.instance;
  }

  get value(): T { return this._value; }
  get next(): IListNode<T> { return this._next; }
  set next(node: IListNode<T>) { this._next = node; }
}

class LinkedList<T> implements IList<T> {
  private head: IListNode<T>;
  private tail: IListNode<T>;

  constructor() {
    this.head = EmptyListNode.instance;
    this.tail = this.head;
  }

  at(index: number): T {
    let cursor = this.head;
    while (index > 0 && !cursor.isEmpty) {
      cursor = cursor.next;
      index--;
    }

    return cursor.value;
  }

  append(value: T): void {
    const newNode = new ListNode(value);
    if (this.tail.isEmpty) {
      this.head = newNode;
      this.tail = this.head;
    } else {
      this.tail.next = newNode;
      this.tail = newNode;
    }
  }

  get values(): T[] {
    let cursor = this.head;
    const values: T[] = [];

    while(!cursor.isEmpty) {
      values.push(cursor.value);
      cursor = cursor.next;
    }

    return values;
  }
}

const list = new LinkedList<number>();
list.append(10);
list.append(20);
list.append(30);
list.append(40);
list.append(50);

console.log(list.values);