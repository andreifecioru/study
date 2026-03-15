import 'titles_set.dart';
import 'image_file.dart';

class Summary {
  /// Returns a new [Summary] instance.
  Summary({
    required this.titles,
    required this.pageid,
    required this.extract,
    required this.extractHtml,
    required this.lang,
    required this.dir,
    this.thumbnail,
    this.originalImage,
    this.url,

    this.description,
  });

  ///
  TitlesSet titles;

  /// The page ID
  int pageid;

  /// First several sentences of an article in plain text
  String extract;

  /// First several sentences of an article in simple HTML format
  String extractHtml;

  ImageFile? thumbnail;

  /// Url to the article on Wikipedia
  String? url;

  ///
  ImageFile? originalImage;

  /// The page language code
  String lang;

  /// The page language direction code
  String dir;

  /// Wikidata description for the page
  String? description;

  bool get hasImage =>
      (originalImage != null || thumbnail != null) && preferredSource != null;

  String? get preferredSource {
    ImageFile? file;

    if (originalImage != null) {
      file = originalImage;
    } else {
      file = thumbnail;
    }

    if (file != null) {
      if (acceptableImageFormats.contains(file.extension.toLowerCase())) {
        return file.source;
      } else {
        return null;
      }
    }

    return null;
  }

  /// Returns a new [Summary] instance
  static Summary fromJson(Map<String, Object?> json) {
    var thumbnail = json['thumbnail'] != null
        ? ImageFile.fromJson(json['thumbnail'] as Map<String, Object?>)
        : null;

    var originalImage = json['originalimage'] != null
        ? ImageFile.fromJson(json['originalimage'] as Map<String, Object?>)
        : null;

    var description = json['description'] != null
        ? json['description'] as String
        : null;
    return switch (json) {
      {
        'titles': final Map<String, Object?> titles,
        'pageid': final int pageid,
        'extract': final String extract,
        'extract_html': final String extractHtml,
        'lang': final String lang,
        'dir': final String dir,
        'content_urls': {
          'desktop': {'page': final String url},
          'mobile': {'page': String _},
        },
      } =>
        Summary(
          titles: TitlesSet.fromJson(titles),
          pageid: pageid,
          extract: extract,
          extractHtml: extractHtml,
          lang: lang,
          dir: dir,
          url: url,
          thumbnail: thumbnail,
          originalImage: originalImage,
          description: description
        ),
      _ => throw FormatException('Could not deserialize Summary, json=$json'),
    };
  }

  @override
  String toString() =>
      'Summary['
      'titles=$titles, '
      'pageid=$pageid, '
      'extract=$extract, '
      'extractHtml=$extractHtml, '
      'thumbnail=${thumbnail ?? 'null'}, '
      'originalImage=${originalImage ?? 'null'}, '
      'lang=$lang, '
      'dir=$dir, '
      'description=$description'
      ']';
}
