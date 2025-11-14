import { readFileSync } from 'node:fs';
import { DataReader } from './Domain';

class CsvFileReader implements DataReader {
  data: string[][] = [];

  constructor(readonly fileName: string) {}

  read(): void {
    this.data = readFileSync(this.fileName, 'utf-8')
      .split('\n')
      .map(row => row.split(','))
  }
}

export { CsvFileReader };