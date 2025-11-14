import { MatchData, MatchDataAnalyzer, Report, MatchResult } from './Domain';

class WinsAnalyzer implements MatchDataAnalyzer {
  constructor(readonly teamName: string) {}

  buildReport(matchData: MatchData[]): Report {
    const winCount = matchData.filter((record: MatchData): boolean => {
      const isHomeWin = record[1] === this.teamName && record[5] === MatchResult.HomeWin;
      const isAwayWin = record[2] === this.teamName && record[5] === MatchResult.AwayWin;

      return isHomeWin || isAwayWin;
    }).length;
    return new Report(`Team ${this.teamName} won ${winCount} games`);
  }
}

class AvgGoalsAnalyzer implements MatchDataAnalyzer {
  constructor(readonly teamName: string) {}

  buildReport(matchData: MatchData[]): Report {
    const goalsPerMatch = matchData
      .filter((match) =>
        match[1] === this.teamName || match[2] === this.teamName
      )
      .map((match) => {
        const homeGoals = match[1] === this.teamName ? match[3] : 0;
        const awayGoals = match[2] === this.teamName ? match[4] : 0;

        return homeGoals + awayGoals;
      });
    const goalCount = goalsPerMatch.reduce((sum, val) => sum + val, 0);
    const matchCount = goalsPerMatch.length;
    const avgGoals = goalCount / matchCount;
    return new Report(`Team ${this.teamName} had an average of ${avgGoals} per match`);
  }
}

export { WinsAnalyzer, AvgGoalsAnalyzer }
