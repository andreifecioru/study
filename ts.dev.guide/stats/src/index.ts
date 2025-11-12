import { CsvFileReader, Record } from './CsvFileReader';
import { MatchResult } from './MatchResult';


const csvReader = new CsvFileReader('./data/football.csv');
csvReader.read();

const records = csvReader.data;
console.log(records[0]);

const manUnitedWins = records.filter((record: Record): boolean => {
  const isHomeWin = record[1] === 'Man United' && record[5] === MatchResult.HomeWin;
  const isAwayWin = record[2] === 'Man United' && record[5] === MatchResult.AwayWin;

  return isHomeWin || isAwayWin;
}).length;

console.log(`Man United wins: ${manUnitedWins}`);

