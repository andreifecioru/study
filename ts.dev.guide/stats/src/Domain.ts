enum MatchResult {
  HomeWin = 'H',
  AwayWin = 'A',
  Draw = 'D'
}

type MatchData = [Date, string, string, number, number, MatchResult, string];

export { MatchData, MatchResult };