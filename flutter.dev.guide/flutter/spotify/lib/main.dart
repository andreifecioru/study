import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:device_preview/device_preview.dart';
import 'package:logging/logging.dart';
import 'package:spotify/core/configs/theme/app_theme.dart';
import 'package:spotify/presentation/splash/pages/splash.dart';

void main() {
  Logger.root.level = .ALL;
  Logger.root.onRecord.listen((record) {
    debugPrint('${record.level.name}: ${record.message}');
  });

  runApp(
    DevicePreview(
      enabled: !kReleaseMode && kIsWeb,
      storage: DevicePreviewStorage.preferences(),
      builder: (context) => const MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      locale: DevicePreview.locale(context),
      builder: DevicePreview.appBuilder,

      theme: AppTheme.lightTheme,
      debugShowCheckedModeBanner: false,
      home: const SplashPage(),
    );
  }
}
