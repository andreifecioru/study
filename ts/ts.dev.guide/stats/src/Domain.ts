enum MatchResult {
  HomeWin = 'H',
  AwayWin = 'A',
  Draw = 'D'
}

interface DataReader {
  read(): void;
  data: string[][];
}

interface DataLoader {
  load(): MatchData[];
}

class Report {
  constructor(readonly content: string) {}
}

interface MatchDataAnalyzer {
  buildReport(matchData: MatchData[]): Report
}

interface ReportPublisher {
  publish(report: Report): void
}

type MatchData = [Date, string, string, number, number, MatchResult, string];

export { 
  MatchData, 
  MatchResult,
  DataReader,
  DataLoader,
  Report,
  MatchDataAnalyzer,
  ReportPublisher
};