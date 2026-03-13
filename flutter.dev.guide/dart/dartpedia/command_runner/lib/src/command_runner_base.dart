import 'dart:async';
import 'dart:collection';
import 'dart:io';
import 'arguments.dart';
import 'exceptions.dart';

class CommandRunner {
  CommandRunner({this.onError});

  final Map<String, Command> _commands = {};

  UnmodifiableSetView<Command> get commands =>
      UnmodifiableSetView({..._commands.values});

  Future<void> run(List<String> args) async {
    try {
      final ArgResults argResults = parseArgs(args);
      final command = argResults.command;

      if (command != null) {
        Object? output = await command.run(argResults);
        print(output.toString());
      }
    } on Exception catch (ex) {
      final errHandler = onError;
      if (errHandler != null) {
        errHandler(ex);
      } else {
        rethrow;
      }
    }
  }

  void addCommand(Command command) {
    _commands[command.name] = command;
    command.cmdRunner = this;
  }

  ArgResults parseArgs(List<String> args) {
    var results = ArgResults();

    if (args.isEmpty) return results;

    // 1st argument must be a command
    if (_commands.containsKey(args.first)) {
      results.command = _commands[args.first];
      args = args.sublist(1);
    } else {
      throw ArgumentException(
        'The 1st argument must be a command.',
        null,
        args.first,
      );
    }

    // only one command is allowd
    final cmdCount = args.where((arg) => _commands.containsKey(arg)).length;
    if (cmdCount > 0) {
      throw ArgumentException(
        'You are allowed to pass in exactly one command',
        null,
        args.first,
      );
    }

    // Section: Handle options, including flags.
    Map<Option, Object?> inputOptions = {};
    int i = 0;
    while (i < args.length) {
      if (args[i].startsWith('-')) {
        var base = _removeDash(args[i]);
        // Throw an exception if an option is not recognized for the given command.
        var option = results.command!.options.firstWhere(
          (option) => option.name == base || option.abbr == base,
          orElse: () {
            throw ArgumentException(
              'Unknown option ${args[i]}',
              results.command!.name,
              args[i],
            );
          },
        );

        if (option.type == OptionType.flag) {
          inputOptions[option] = true;
          i++;
          continue;
        }

        if (option.type == OptionType.option) {
          // Throw an exception if an option requires an argument but none is given.
          if (i + 1 >= args.length) {
            throw ArgumentException(
              'Option ${option.name} requires an argument',
              results.command!.name,
              option.name,
            );
          }
          if (args[i + 1].startsWith('-')) {
            throw ArgumentException(
              'Option ${option.name} requires an argument, but got another option ${args[i + 1]}',
              results.command!.name,
              option.name,
            );
          }
          var arg = args[i + 1];
          inputOptions[option] = arg;
          i++;
        }
      } else {
        // Throw an exception if more than one positional argument is provided.
        if (results.commandArg != null && results.commandArg!.isNotEmpty) {
          throw ArgumentException(
            'Commands can only have up to one argument.',
            results.command!.name,
            args[i],
          );
        }
        results.commandArg = args[i];
      }
      i++;
    }
    results.options = inputOptions;

    return results;
  }

  String _removeDash(String input) {
    if (input.startsWith('--')) {
      return input.substring(2);
    }
    if (input.startsWith('-')) {
      return input.substring(1);
    }
    return input;
  }

  String get usage {
    final exeFile = Platform.script.path.split('/').last;
    return 'Usage: dart bin/$exeFile <command> [commandArg?] [...options]';
  }

  FutureOr<void> Function(Object)? onError;
}
