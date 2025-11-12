import { CsvFileReader } from "./CsvFileReader";
import { MatchData, MatchResult } from "./Domain";
import { parseDate } from "./Utils";

class MatchDataReader extends CsvFileReader<MatchData> {
  override parseRow(row: string[]): MatchData {
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

export { MatchDataReader };