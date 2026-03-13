import 'dart:io';
import 'package:logging/logging.dart';

Logger initFileLogger(String name) {
  hierarchicalLoggingEnabled = true;
  final logger = Logger(name);
  final now = DateTime.now();

  final scriptFile = File(Platform.script.toFilePath());
  final projectDir = scriptFile.parent.parent.path;
  final dir = Directory('$projectDir/logs');
  if (!dir.existsSync()) dir.createSync();
  final logFile = File(
    '${dir.path}/${now.year}_${now.month}_${now.day}_$name.txt',
  );

  // Set the logger level to ALL, so it logs all messages regardless of severity.
  // Level.ALL is useful for development and debugging, but you'll likely want to
  // use a more restrictive level like Level.INFO or Level.WARNING in production.
  logger.level = Level.ALL;

  // Listen for log records and write each one to the log file.
  logger.onRecord.listen((record) {
    final msg =
        '[${record.time} - ${record.loggerName}] ${record.level.name}: ${record.message}';
    logFile.writeAsStringSync('$msg \n', mode: FileMode.append);
  });

  return logger;
}
