package org.afecioru.study.lsb3.ch5.services;

import lombok.Data;

import org.afecioru.study.lsb3.ch5.persistence.entities.UserAccountEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.afecioru.study.lsb3.ch5.persistence.repositories.UserAccountRepository;


@Data
@Service
public class UserAccountService implements UserDetailsService {
    static private final String DEFAULT_PASS = "pass";

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userAccount = repository.findByUsername(username);
        return User.withUsername(userAccount.getUsername())
            .passwordEncoder(passwordEncoder::encode)
            .password(DEFAULT_PASS)
            .authorities(userAccount.getAuthorities())
            .build();
    }
}
