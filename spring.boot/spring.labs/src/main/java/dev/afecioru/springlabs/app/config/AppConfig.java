package dev.afecioru.springlabs.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({
    "dev.afecioru.springlabs.external.*",
    "dev.afecioru.springlabs.app.config.*",
    "dev.afecioru.springlabs.app.api.*",
    "dev.afecioru.springlabs.core.*"
})
@EnableJpaRepositories({
    "dev.afecioru.springlabs.external.*"
})
@EntityScan({
    "dev.afecioru.springlabs.external.*"
})

public class AppConfig {}
