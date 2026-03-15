# Wiki Reader Tutorial — Dev Log

## JSON Parsing Pattern

The general pattern for deserializing JSON in Dart:

- Define a data class with fields matching the JSON structure
- Define a `fromJson(Map<String, Object?>)` static factory method that pattern matches the map
- Throw a `FormatException` if no pattern arm matches
- Apply recursively for nested objects

`Object?` (not `dynamic`) is intentional — it forces explicit type assertion before use, which is null safety applied to heterogeneous data.

---

## Design Smell: Combinatorial `switch` Arms for Optional Fields

**File:** `lib/summary.dart` — `Summary.fromJson`

`Summary` has two optional fields (`thumbnail`, `description`). Because Dart map
patterns require a key to be *present* to match (missing key ≠ `null`), the
tutorial enumerates all 2² = 4 combinations as separate `switch` arms.

This doesn't scale — 3 optional fields → 8 arms, 4 → 16.

**Better approach:** read optional keys individually outside a switch:

```dart
Summary(
  // ...required fields via pattern match or direct access...
  thumbnail: json['thumbnail'] != null
      ? ImageFile.fromJson(json['thumbnail'] as Map<String, Object?>)
      : null,
  description: json['description'] as String?,
)
```

Linear in the number of fields, not exponential. Revisit when the tutorial is complete.

---

## TODO: Inject `http.Client` into `ArticleModel`

**File:** `lib/models/article.dart`

Currently uses the package-level `get(uri)` function from `package:http/http.dart`.
This works but is untestable — you can't swap it out without hitting the real network.

**Better approach:** inject an `http.Client` instance via the constructor:

```dart
class ArticleModel {
  ArticleModel({http.Client? client}) : _client = client ?? http.Client();
  final http.Client _client;

  Future<Summary> getRandomArticle() async {
    final response = await _client.get(uri);
    // ...
  }
}
```

In tests, pass a mock client. In production, the default kicks in.
Revisit when the tutorial is complete.

---

## Sealed Classes for ViewModel State

The tutorial uses three separate fields (`summary`, `errorMessage`, `loading`) to represent screen state. This allows impossible combinations (e.g. `loading = true` AND `summary != null`).

**Better approach:** model state as a sealed class hierarchy — one field, no impossible states:

```dart
sealed class ArticleState {}
final class Idle extends ArticleState {}
final class Loading extends ArticleState {}
final class Loaded extends ArticleState {
  Loaded(this.summary);
  final Summary summary;
}
final class LoadError extends ArticleState {
  LoadError(this.errorMessage);
  final String errorMessage;
}
```

ViewModel holds `ArticleState state = Idle()` and assigns a new instance on each transition.

The switch in the View is then exhaustive — the compiler enforces all cases are handled:

```dart
switch (viewModel.state) {
  Idle() || Loading() => Center(child: CircularProgressIndicator()),
  LoadError(:final errorMessage) => Center(child: Text(errorMessage)),
  Loaded(:final summary) => ArticlePage(summary: summary, ...),
}
```

The `:field` syntax in patterns is Dart 3 destructuring shorthand for `FieldName(fieldName: final fieldName)`.