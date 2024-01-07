package org.afecioru.study.lsb3.ch5.config.web;

import org.afecioru.study.lsb3.ch5.persistence.entities.BookEntity;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.afecioru.study.lsb3.ch5.persistence.repositories.BookRepository;


@Configuration
public class WebConfig {
    @Bean
    ApplicationRunner dataLoader(BookRepository repository) {
        return args -> {
            repository.save(new BookEntity("Same As Ever", "Morgan Housel", 2_000_000));
            repository.save(new BookEntity("Tools Of Titans", "Tim Ferris", 1_500_000));
            repository.save(new BookEntity("Four Hour Week", "Tim Ferris", 1_000_000));
        };
    }
}
