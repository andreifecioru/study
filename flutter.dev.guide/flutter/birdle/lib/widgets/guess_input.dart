import 'package:flutter/material.dart';

class GuessInput extends StatelessWidget {
  GuessInput({super.key, required this.onSubmitGuess});

  final ValueChanged<String> onSubmitGuess;
  final _textEditingController = TextEditingController();
  final _focusNode = FocusNode();

  void _onSubmitted(String? _) {
    onSubmitGuess(_textEditingController.text.trim());
    _textEditingController.clear();
    _focusNode.requestFocus();
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: .start,
      children: [
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              maxLength: 5,
              autofocus: true,
              focusNode: _focusNode,
              decoration: InputDecoration(
                border: OutlineInputBorder(borderRadius: .all(.circular(35))),
                suffixIcon: IconButton(
                  onPressed: () => _onSubmitted(null),
                  icon: Icon(Icons.arrow_circle_up),
                ),
              ),
              controller: _textEditingController,
              onSubmitted: _onSubmitted,
            ),
          ),
        ),
      ],
    );
  }
}