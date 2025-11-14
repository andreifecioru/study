
import { CsvFileReader } from "./CsvFileReader";
import { DataReader } from "./Domain";
import { MatchData, MatchResult } from "./Domain";
import { parseDate } from "./Utils";

class MatchDataLoader {
  static fromCsvFile(filePath: string): MatchDataLoader {
    return new MatchDataLoader(
      new CsvFileReader(filePath)
    );
  }

  constructor(readonly reader: DataReader) {}

  load(): MatchData[] {
    this.reader.read();
    return this.reader.data.map(this.parseRow);
  }

  private parseRow(row: string[]): MatchData {
    return [
      parseDate(row[0]),
      row[1],
      row[2],
      parseInt(row[3]),
      parseInt(row[4]),
      row[5] as MatchResult,
      row[6]
    ];
  }
}

export { MatchDataLoader };