import 'package:flutter/cupertino.dart';

void main() {
  runApp(const RolodexApp());
}

class RolodexApp extends StatelessWidget {
  const RolodexApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const CupertinoApp(
      home: CupertinoPageScaffold(
        child: Center(
          child: Text('Hello World!'),
        ),
      ),
    );
  }
}
