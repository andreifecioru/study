import { Report, ReportPublisher } from './Domain';

class ConsolePublisher implements ReportPublisher {
  publish(report: Report): void {
      console.log(report.content);
  }
}

export { ConsolePublisher };