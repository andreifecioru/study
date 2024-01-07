package org.afecioru.study.lsb3.ch4.config.security;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.DefaultSecurityFilterChain;

import org.afecioru.study.lsb3.ch4.persistence.entities.UserAccount;
import org.afecioru.study.lsb3.ch4.persistence.repositories.UserAccountRepository;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository accountRepository,
                                                 PasswordEncoder passwordEncoder) {
        return username -> {
            var userAccount = accountRepository.findByUsername(username);
            return userAccountToUserDetails(userAccount, passwordEncoder);
        };
    }

    @Bean
    public DefaultSecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {
        http
            .csrf(Customizer.withDefaults())
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers("/login", "/youtube").permitAll()
                    .requestMatchers(HttpMethod.POST, "/create", "/search").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/csrf/token").permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public ApplicationRunner createUsers(UserAccountRepository accountRepository,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            accountRepository.save(new UserAccount(
                "guest",
                passwordEncoder.encode("pass"),
                "ROLE_USER"));

            accountRepository.save(new UserAccount(
                "admin",
                passwordEncoder.encode("pass"),
                "ROLE_ADMIN"));
        };
    }

    private static UserDetails userAccountToUserDetails(UserAccount userAccount,
                                                        PasswordEncoder passwordEncoder) {
        return User
            .withUsername(userAccount.getUsername())
            .passwordEncoder(passwordEncoder::encode)
            .password("pass")
            .authorities(userAccount.getAuthorities())
            .build();
    }
}
