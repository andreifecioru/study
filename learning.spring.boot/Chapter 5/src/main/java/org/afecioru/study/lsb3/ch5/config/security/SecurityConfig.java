package org.afecioru.study.lsb3.ch5.config.security;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.afecioru.study.lsb3.ch5.persistence.repositories.UserAccountRepository;
import org.afecioru.study.lsb3.ch5.persistence.entities.UserAccountEntity;
import org.springframework.security.web.DefaultSecurityFilterChain;


@Configuration
public class SecurityConfig {
    static private final String DEFAULT_PASS = "pass";

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationRunner loadUsers(UserAccountRepository repository,
                                PasswordEncoder passwordEncoder) {
        var password = passwordEncoder.encode(DEFAULT_PASS);
        return args -> {
            repository.save(new UserAccountEntity("admin", password, "ROLE_ADMIN"));
            repository.save(new UserAccountEntity("guest", password, "ROLE_USER"));
        };
    }

    @Bean
    DefaultSecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {
        return http
            .csrf(Customizer.withDefaults())
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers("/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/add", "/delete/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .build();
    }
}
