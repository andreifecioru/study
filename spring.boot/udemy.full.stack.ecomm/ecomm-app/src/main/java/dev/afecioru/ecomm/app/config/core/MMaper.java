package dev.afecioru.ecomm.app.config.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MMaper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
