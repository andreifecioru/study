import { readFileSync } from 'node:fs';
import { MatchResult } from './MatchResult';

type Record = [Date, string, string, number, number, MatchResult, string]

class CsvFileReader {
  data: Record[] = [];

  constructor(readonly fileName: string) {}

  read(): void {
    this.data = readFileSync(this.fileName, 'utf-8')
      .split('\n')
      .map(row => row.split(','))
      .map(row => {
        return [
          this.parseDate(row[0]),
          row[1],
          row[2],
          parseInt(row[3]),
          parseInt(row[4]),
          row[5] as MatchResult,
          row[6]
        ]
      });
  }

  private parseDate(dateStr: string): Date {
    const [day, month, year] = dateStr.split('/')
      .map(part => parseInt(part, 10));
    return new Date(year, month - 1, day);
  }
}

export { CsvFileReader, Record };