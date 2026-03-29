# Device Preview Setup

Using `device_preview` to simulate mobile screen dimensions when running Flutter
on the browser (`-d chrome`), as an alternative to an Android emulator.

## Installation

Run from the project root:

```sh
flutter pub add device_preview --dev
```

This adds it as a dev dependency (stripped from release builds):

```yaml
# pubspec.yaml
dev_dependencies:
  device_preview: ^1.3.1
```

## Changes to main.dart

```dart
import 'package:flutter/foundation.dart';        // for kReleaseMode
import 'package:device_preview/device_preview.dart';

void main() {
  runApp(
    DevicePreview(
      enabled: !kReleaseMode,
      builder: (context) => const MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      locale: DevicePreview.locale(context),     // honours locale overrides
      builder: DevicePreview.appBuilder,          // renders the device frame
      // ... rest of your MaterialApp props
    );
  }
}
```

Key points:
- `enabled: !kReleaseMode` — the overlay is active in debug/profile builds only,
  completely absent in release builds
- `locale` + `builder` on `MaterialApp` are required; without them the app
  ignores the selected device dimensions
- `useInheritedMediaQuery` (sometimes mentioned in older docs) is deprecated
  since Flutter 3.7 — do not add it

## Usage

Run with:

```sh
flutter run -d chrome
```

A toolbar appears on the left. Navigate to **Device model → Android** and pick
a device. The closest available match to the Samsung Galaxy S25 Ultra is the
**Samsung Galaxy Note 20 Ultra** (412×883 @3.5).

Switching devices updates `MediaQuery` in real time — no restart needed.

## Caveats

- `SafeArea` insets, scroll physics, and text rendering differ slightly from a
  real device. Use this for layout iteration; validate on a physical device for
  anything touch/gesture/inset sensitive.
- The toolbar is part of the running app — it won't appear in screenshots or
  recordings taken from within the app.