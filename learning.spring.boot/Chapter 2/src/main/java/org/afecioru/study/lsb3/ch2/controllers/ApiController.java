package org.afecioru.study.lsb3.ch2.controllers;

import lombok.Data;
import org.afecioru.study.lsb3.ch2.models.Video;
import org.afecioru.study.lsb3.ch2.services.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
public class ApiController {
    private final VideoService videoService;

    @GetMapping("/api/v1/videos")
    public List<Video> allVideos() {
       return videoService.getVideos();
    }

    @PostMapping("/api/v1/videos")
    public Video newVideo(@RequestBody Video newVideo) {
        return videoService.createVideo(newVideo);
    }
}
