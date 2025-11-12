import { MatchData, MatchResult } from './Domain';
import { MatchDataReader } from './MatchDataReader';


const matchDataReader = new MatchDataReader('./data/football.csv');
matchDataReader.read();

const records = matchDataReader.data;
console.log(records[0]);

const manUnitedWins = records.filter((record: MatchData): boolean => {
  const isHomeWin = record[1] === 'Man United' && record[5] === MatchResult.HomeWin;
  const isAwayWin = record[2] === 'Man United' && record[5] === MatchResult.AwayWin;

  return isHomeWin || isAwayWin;
}).length;

console.log(`Man United wins: ${manUnitedWins}`);

