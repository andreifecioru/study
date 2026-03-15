import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

import '../game.dart';
import 'tile.dart';
import 'guess_input.dart';

class GamePage extends StatefulWidget {
  const GamePage({super.key});

  @override
  State<GamePage> createState() => _GameState();
}

class _GameState extends State<GamePage> {
  final _log = Logger('GamePage');

  void _onGuessSubmitted(String value) {
    _log.info('User submitted: $value');
    setState(() {
      _game.guess(value);
    });
  }

  late Game _game;

  @override
  void initState() {
    super.initState();
    _game = Game();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        spacing: 5.0,
        children: [
          ..._game.guesses.map(
            (guess) => Row(
              spacing: 5.0,
              children: [
                ...guess.map((letter) => Tile(letter.char, letter.type)),
              ],
            ),
          ),
          GuessInput(onSubmitGuess: _onGuessSubmitted),
        ],
      ),
    );
  }
}
