import 'package:flutter/material.dart';
import '../game.dart';

class Tile extends StatelessWidget {
  const Tile(this.letter, this.hitType, {super.key});

  final String letter;
  final HitType hitType;

  @override
  Widget build(BuildContext context) {
    return AnimatedContainer(
      duration: Duration(milliseconds: 500),
      curve: Curves.bounceIn,
      width: 60.0,
      height: 60,
      decoration: BoxDecoration(
        border: .all(color: Colors.grey.shade300),
        color: switch (hitType) {
          .hit => Colors.green,
          .partial => Colors.yellow,
          .miss => Colors.grey,
          _ => Colors.white,
        },
      ),
      child: Center(child: Text(letter)),
    );
  }
}
