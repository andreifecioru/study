package org.afecioru.study.lsb3.ch3.config;

import org.afecioru.study.lsb3.ch3.services.VideoService;
import org.afecioru.study.lsb3.ch3.services.dto.VideoDTO;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {
    @Bean
    public ApplicationRunner dataLoader(VideoService videoService) {
        return args -> {
            videoService.addVideo(new VideoDTO("Test video one", "First test video"));
            videoService.addVideo(new VideoDTO("Test video two", "Second test video"));
            videoService.addVideo(new VideoDTO("Test video three", "Third test video"));
            videoService.addVideo(new VideoDTO("Test video four", "Fourth test video"));
        };
    }
}
