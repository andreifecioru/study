import {Comparable} from './Comparable';

class Person implements Comparable<Person> {

  constructor(readonly name: string, readonly age: number) {
  }

  compare(other: Person): number {
    if (this.age === other.age) {
      return 0
    } else if (this.age > other.age) {
      return 1;
    } else {
      return -1;
    }
  }
}

export { Person };