import 'package:flutter/material.dart';
import '../view_models/article_view_model.dart';
import '../models/article.dart';
import './widgets/article_page.dart';

class ArticleView extends StatelessWidget {
  ArticleView({super.key});

  final viewModel = ArticleViewModel(ArticleModel());

  void nextArticleCbk() {
    viewModel.getRandomArticleSummary();
  }

  Widget buiderCallback(BuildContext context, Widget? child) {
    return switch (viewModel.state) {
      Idle() || Loading() => Center(child: CircularProgressIndicator()),
      LoadError(:final errorMessage) => Center(
        child: Text('ERROR: $errorMessage'),
      ),
      Loaded(:final summary) => ArticlePage(
        summary: summary,
        nextArticleCbk: nextArticleCbk,
      ),
    };
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Wiki Reader')),
      body: ListenableBuilder(listenable: viewModel, builder: buiderCallback),
    );
  }
}
