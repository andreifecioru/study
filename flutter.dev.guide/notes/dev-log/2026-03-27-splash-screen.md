# Splash Screen Feature — 2026-03-27

Spotify clone (YouTube course). Building an in-app splash screen — a Flutter
widget/route shown after the engine starts, before navigating to home.

---

## Feature folder structure

Each feature lives under `lib/presentation/<feature>/` with three sub-folders:

```
lib/presentation/
└── splash/
    ├── pages/      # top-level routable screens (StatelessWidget or StatefulWidget)
    ├── widgets/    # smaller composable UI pieces local to this feature
    └── bloc/       # BLoC state management (coming later)
```

`pages/` holds the screen the router pushes. `widgets/` holds things that are
too feature-specific to live in a shared `common/` folder but too small to be
a page themselves.

---

## New dependency: flutter_svg

Vector assets (`.svg`) are not supported by Flutter's built-in `Image` widget.
The `flutter_svg` package provides `SvgPicture.asset()` as a drop-in:

```dart
SvgPicture.asset(AppVectors.logo)
```

Asset paths are centralised in `AppVectors` (mirrors the `AppColors` pattern):

```dart
class AppVectors {
  static const String _basePath = 'assets/vectors';
  static const String _format = '.svg';
  static const String logo = '$_basePath/spotify_logo$_format';
}
```

Added via `flutter pub add flutter_svg` (lets pub resolve the version constraint).

---

## Timer-based navigation

`SplashPage` was converted to a `StatefulWidget` to gain lifecycle hooks.
Navigation fires after a 2-second delay via a private `_redirect` method.
Called from `initState` (which cannot itself be `async`):

```dart
@override
void initState() {
  super.initState();
  _redirect();
}
```

`pushReplacement` removes the splash from the navigation stack so the back
button on `GetStartedPage` does not return to the splash.

---

## Navigator — mental model (to revisit in depth later)

`Navigator` is a stack of routes managed by a widget that `MaterialApp` inserts
near the root of the tree. Push = go forward, pop = go back (back button).

`Navigator.of(context)` walks *up* the widget tree from `context` to find the
nearest `Navigator` ancestor and returns its `NavigatorState` — which is why
`context` is required.

A `Route` is a wrapper around a screen. `MaterialPageRoute` is the standard
flavour; its `builder` callback is called lazily when the route is shown.

Common stack operations:

| Method | Stack effect |
|---|---|
| `push` | adds on top — back returns to previous screen |
| `pushReplacement` | replaces top — back skips the replaced screen |
| `pushAndRemoveUntil` | push + clear everything below a predicate |

`Navigator` is just a widget — not special infrastructure. This is why Flutter
supports nested navigators (e.g. each tab in a tab bar has its own back stack).

---

## Gotcha: BuildContext across async gaps

Using `context` after an `await` is unsafe. In the time the `Future` is
pending, the widget could be disposed — the user navigated away, the OS
reclaimed the screen, etc. At that point `context` is dangling.

The `use_build_context_synchronously` lint flags exactly this.

**Preferred fix — capture the navigator before the async gap:**

```dart
Future<void> _redirect() async {
  final navigator = Navigator.of(context); // synchronous — safe

  await Future.delayed(const Duration(seconds: 2));

  navigator.pushReplacement(             // no context access here
    MaterialPageRoute(builder: (context) => const GetStartedPage()),
  );
}
```

`Navigator.of(context)` returns a `NavigatorState` object. Capturing it before
the `await` satisfies the lint properly (not as a workaround). After the gap,
you're calling a method on a plain Dart object — no `context` involved.

This works because the `Navigator` widget lives near the root (inserted by
`MaterialApp`) and won't be disposed just because `SplashPage` is.

**Alternative — `mounted` check:**

```dart
if (mounted) {
  Navigator.pushReplacement(context, ...);
}
```

Also valid. The difference: `mounted` bails out silently if the widget is
disposed; the captured navigator still navigates. For a splash screen going
to a fixed destination either works — prefer the capture pattern as it expresses
intent more clearly ("give me the navigator now") and handles the lint at the
source rather than after the fact.