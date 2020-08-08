export interface IList<T> {
  at(index: number): T;
  append(value: T): void;
  readonly values: T[];
}