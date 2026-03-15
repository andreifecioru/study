import 'dart:io';

import 'package:flutter/material.dart';
import 'package:logging/logging.dart';
import '../models/article.dart';
import '../models/summary.dart';

class ArticleViewModel extends ChangeNotifier {
  final Logger _log = Logger('ArticleViewModel');

  ArticleViewModel(this.model) {
    getRandomArticleSummary();
  }
  final ArticleModel model;

  ArticleState state = Idle();

  Future<void> getRandomArticleSummary() async {
    state = Loading();
    notifyListeners();

    try {
      var summary = await model.getRandomArticle();
      _log.info('Article summary: $summary');
      state = Loaded(summary);
    } on HttpException catch (error) {
      _log.warning('ERROR: ${error.message}');
      state = LoadError(error.message);
    } catch (error) {
      _log.severe('Unexpected error: $error');
      state = LoadError('Unexpected error occurred.');
    } finally {
      notifyListeners();
    }
  }
}

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
