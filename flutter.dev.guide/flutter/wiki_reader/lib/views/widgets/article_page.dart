import 'package:flutter/material.dart';
import 'package:wiki_reader/models/summary.dart';

class ArticlePage extends StatelessWidget {
  const ArticlePage({
    super.key,
    required this.summary,
    required this.nextArticleCbk,
  });

  final Summary summary;
  final VoidCallback nextArticleCbk;

  @override
  Widget build(BuildContext context) {
    return Column(
      spacing: 10.0,
      children: [
        Expanded(
          child: SingleChildScrollView(child: ArticleWidget(summary: summary)),
        ),
        Padding(
          padding: EdgeInsets.only(bottom: 16.0),
          child: SafeArea(
            child: ElevatedButton(
              onPressed: nextArticleCbk,
              child: Text('Next article'),
            ),
          ),
        ),
      ],
    );
  }
}

class ArticleWidget extends StatelessWidget {
  const ArticleWidget({super.key, required this.summary});
  final Summary summary;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(8.0),
      child: Column(
        spacing: 10.0,
        children: [
          if (summary.hasImage) Image.network(summary.originalImage!.source),
          Text(
            summary.titles.normalized,
            overflow: .ellipsis,
            style: TextTheme.of(context).displaySmall,
          ),
          if (summary.description != null)
            Text(
              summary.description!,
              overflow: TextOverflow.ellipsis,
              style: TextTheme.of(context).bodySmall,
            ),
          Text(summary.extract),
        ],
      ),
    );
  }
}
