package org.afecioru.study.lsb3.ch4.config.oauth2.google;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface YouTubeExchange {

    @GetExchange("/search?part=snippet&type=video")
    SearchListResponse channelVideos(
        @RequestParam String channelId,
        @RequestParam int maxResults,
        @RequestParam Sort order
    );

    enum Sort {
        DATE("date"),
        VIEW_COUNT("viewCount"),
        TITLE("title"),
        RATING("rating");

        private final String type;

        Sort(String type) {
            this.type = type;
        }
    }

    record SearchId(String kind, String videoId, String channelId, String playlistId) {}

    record SearchThumbnail(String url, Integer width, Integer height) {}

    record SearchSnippet(String publishedAt, String channelId, String title,
                         String description, Map<String, SearchThumbnail> thumbnails,
                         String channelTitle) {

        public String shortDescription() {
            return description.length() <= 100
                ? description
                : description.substring(0, 100);
        }

        public SearchThumbnail thumbnail() {
            return thumbnails.entrySet().stream()
                .filter(entry -> entry.getKey().equals("default"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
        }
    }

    record PageInfo(Integer totalResult, Integer resultsPage) {}

    record SearchResult(String kind, String etag, SearchId id, SearchSnippet snippet) {}

    record SearchListResponse(String kind, String etag, String nextPageToken,
                             String prevPageToken, PageInfo pageInfo, SearchResult[] items) {}

}
