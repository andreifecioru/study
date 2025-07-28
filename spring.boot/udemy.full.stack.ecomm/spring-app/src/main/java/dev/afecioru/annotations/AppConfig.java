package dev.afecioru.annotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("dev.afecioru.annotations")
public class AppConfig {
    @Bean
    public Employee john() {
        return new Employee("John", 30);
    }

    @Bean
    public Employee jane() {
        return new Employee("Jane", 20);
    }
}
