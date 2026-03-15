# Birdle Tutorial Session — 2026-03-15

## What we built
Completed the official Flutter introductory tutorial: a Wordle-clone called **Birdle**.
Project lives at `flutter/birdle/`.

## Key concepts covered

### Widget fundamentals
- `StatelessWidget` — immutable, build method is the render function
- `StatefulWidget` + `State<T>` — two-class split: widget is discarded freely, State persists across rebuilds
- `setState()` — triggers rebuild, same contract as React's useState setter
- `BuildContext` — widget's position in the tree; used for theme, navigation, inherited data
- `const` constructors — compile-time interning, free perf optimization; infectious (whole subtree must be const)
- `AppBar` does NOT have a const constructor — breaks the const chain at that level

### Layout
- Composition over inheritance — custom widgets are just `build()` returning nested widgets
- `Row`, `Column`, `Expanded`, `Center`, `Align`
- `suffixIcon` in `InputDecoration` — preferred over a separate `IconButton` next to `TextField` for robust alignment
- Dart 3.7 formatter no longer uses trailing commas to control expansion — fully automatic

### Input handling
- `TextField` with `onSubmitted` callback
- `TextEditingController` — imperative handle to text field state (analogous to React `useRef`); must be disposed in `dispose()` (analogous to useEffect cleanup)
- Callback passed down from stateful parent to child — "lifting state up" pattern, same as React

### Animations
- `AnimatedContainer` — drop-in replacement for `Container`; add `duration`, animates property changes implicitly
- Bounce curves are wrong for color transitions — designed for positional/size animations
- 500ms duration felt right for tile color transitions

### Logging
- Using the `logging` package (not `dart:developer`)
- Setup in `main()`: `Logger.root.level = Level.ALL` + `onRecord.listen(...)`
- Per-class loggers: `final _log = Logger('ClassName')`
- Visible in VSCode DevTools > Logging panel

### Project structure (final)
```
flutter/birdle/lib/
  main.dart              — entry point + MainApp only
  game.dart              — domain model (Game, Word, WordUtils, HitType, Letter)
  widgets/
    tile.dart
    game_page.dart
    guess_input.dart
```

## game.dart highlights
- `Letter` is a Dart **record** (named tuple): `({String char, HitType type})`
- `Word` uses `IterableMixin` — implement `iterator`, get full Iterable API free (like Scala's TraversableLike)
- Factory constructors (`Word.empty()`, `Word.fromString()`, etc.) — Dart's equivalent of companion object apply methods
- `UnmodifiableListView` — read-only view pattern over mutable internal list
- `late` — defers null-safety check to runtime; legitimate when initialization depends on constructor logic
- `evaluateGuess` — two-pass Bulls and Cows algorithm; `removed` HitType used as consumed marker

## Environment issues resolved
- Linux desktop target: missing `libstdc++.so` symlink → `sudo ln -s /usr/lib/gcc/x86_64-linux-gnu/11/libstdc++.so /usr/lib/x86_64-linux-gnu/libstdc++.so`
- Missing C++ headers: clang 14 expected GCC 12 → `sudo apt install gcc-12 libstdc++-12-dev`
- CMake install prefix issue → `flutter clean && flutter pub get`

## Next tutorial
**wiki_reader** — fetching Wikipedia API, `ChangeNotifier`, MVVM pattern.
Project scaffolded at `flutter/wiki_reader/`.
