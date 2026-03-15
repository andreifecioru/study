import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

import 'views/article_view.dart';

void main() {
  Logger.root.level = .ALL;
  Logger.root.onRecord.listen((record) {
    debugPrint('${record.level.name}: ${record.message}');
  });
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: ArticleView());
  }
}
