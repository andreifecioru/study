package dev.afecioru.ecomm.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan({
        "dev.afecioru.ecomm.external.*",
        "dev.afecioru.ecomm.app.config.*",
        "dev.afecioru.ecomm.core.*"
})
@EnableJpaRepositories({
        "dev.afecioru.ecomm.external.*"
})
@EntityScan({
        "dev.afecioru.ecomm.external.*"
})
public class AppConfig {
    // This is an empty configuration
    // It is just a place to configure the component/repo/entity scanning
}
