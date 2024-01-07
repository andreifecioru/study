package org.afecioru.study.lsb3.ch4.config.web;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.afecioru.study.lsb3.ch4.persistence.entities.VideoEntity;
import org.afecioru.study.lsb3.ch4.persistence.repositories.VideoRepository;


@Configuration
public class WebConfig {
    @Bean
    public ApplicationRunner dataLoader(VideoRepository repository) {
        return args -> {
            repository.save(new VideoEntity(null, "Video One", "First video description."));
            repository.save(new VideoEntity(null, "Video Two", "Second video description."));
            repository.save(new VideoEntity(null, "Video Three", "Third video description."));
            repository.save(new VideoEntity(null, "Video Four", "Fourth video description."));
        };
    }
}
