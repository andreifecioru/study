package dev.afecioru.socialmedia.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan({
        "dev.afecioru.socialmedia.external.*",
        "dev.afecioru.socialmedia.app.config.*",
        "dev.afecioru.socialmedia.core.*"
})

@EnableJpaRepositories({
        "dev.afecioru.socialmedia.external.*"
})
@EntityScan({
        "dev.afecioru.socialmedia.external.*"
})
public class AppConfig {
    // This is an empty configuration
    // It is just a place to configure the component/repo/entity scanning
}
