import { DataLoader, MatchData, MatchDataAnalyzer, ReportPublisher } from './Domain';
import { MatchDataLoader } from './Loaders';
import { CsvFileReader } from './CsvFileReader';
import { WinsAnalyzer } from './Analyzers';
import { ConsolePublisher } from './Publishers';

const teamName = 'Man United';

class Summary {
  constructor(
    readonly analyzer: MatchDataAnalyzer,
    readonly publisher: ReportPublisher
  ) {}

  run(matches: MatchData[]): void {
    this.publisher.publish(
      this.analyzer.buildReport(matches)
    );
  }
}

const matches = MatchDataLoader.fromCsvFile('./data/input/football.csv').load();

const summary = new Summary(
  new WinsAnalyzer(teamName),
  new ConsolePublisher()
);

summary.run(matches);


