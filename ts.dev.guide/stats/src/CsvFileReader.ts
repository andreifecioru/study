import { readFileSync } from 'node:fs';

abstract class CsvFileReader<T> {
  data: T[] = [];

  constructor(readonly fileName: string) {}

  read(): void {
    this.data = readFileSync(this.fileName, 'utf-8')
      .split('\n')
      .map(row => row.split(','))
      .map(this.parseRow);
  }

  abstract parseRow(row: string[]): T;
}

export { CsvFileReader };