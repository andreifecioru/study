package org.afecioru.study.lsb3.ch5.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.afecioru.study.lsb3.ch5.persistence.entities.UserAccountEntity;
import org.afecioru.study.lsb3.ch5.persistence.repositories.UserAccountRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public final class UserAccountServiceTest {
    private UserAccountService service;

    @Mock
    private UserAccountRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        service = new UserAccountService(repository, passwordEncoder);
        when(passwordEncoder.encode(anyString())).thenReturn("pass");
    }

    @Test
    void loadUserWithUsername() {
        // Arrange
        var expected = new UserAccountEntity("guest", "pass", "ROLE_USER");
        when(repository.findByUsername("guest")).thenReturn(expected);

        // Act
        var userDetails = service.loadUserByUsername("guest");
        var actual = userAccountFromUserDetails(userDetails);

        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    private UserAccountEntity userAccountFromUserDetails(UserDetails userDetails) {
        return new UserAccountEntity(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities().stream()
                .map(Object::toString)
                .toList()
                .toArray(new String[0])
        );
    }
}
