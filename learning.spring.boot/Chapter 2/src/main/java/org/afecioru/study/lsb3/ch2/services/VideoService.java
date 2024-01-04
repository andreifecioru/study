package org.afecioru.study.lsb3.ch2.services;

import org.afecioru.study.lsb3.ch2.models.Video;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    private static List<Video> videos = List.of(
        new Video("Need help with Spring Boot 3 App?"),
        new Video("Don't do this to your own code!"),
        new Video("Secrets to fix broken code.")
    );

    public List<Video> getVideos() {
        return videos;
    }

    public Video createVideo(Video newVideo) {
        var updated = new ArrayList<>(videos);
        updated.add(newVideo);
        videos = List.copyOf(updated);
        return newVideo;
    }
}
