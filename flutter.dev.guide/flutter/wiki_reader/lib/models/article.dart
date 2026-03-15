import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart';

import 'summary.dart';

class ArticleModel {
  Future<Summary> getRandomArticle() async {
    final uri = Uri.https(
      'en.wikipedia.org',
      '/api/rest_v1/page/random/summary',
    );

    final response = await get(uri);

    if (response.statusCode != 200) {
      throw HttpException('Failed to fetch article.');
    }

    return Summary.fromJson(jsonDecode(response.body) as Map<String, Object?>);
  }
}
