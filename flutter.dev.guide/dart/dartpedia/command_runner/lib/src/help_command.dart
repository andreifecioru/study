import 'dart:async';
import 'arguments.dart';

class HelpCommand extends Command {
  HelpCommand() {
    addFlag(
      'verbose',
      abbr: 'v',
      help: 'When true, print each command and its options',
    );

    addOption('command', abbr: 'c', help: "Print the command's verbose usage");
  }

  @override
  String get name => 'help';

  @override
  String get description => 'Print usage info to the command line.';

  @override
  String get help => 'Print usage info.';

  @override
  Future<Object?> run(ArgResults args) async {
    var usage = cmdRunner.usage;
    for (var command in cmdRunner.commands) {
      usage += '\n ${command.usage}';
    }

    return usage;
  }
}
