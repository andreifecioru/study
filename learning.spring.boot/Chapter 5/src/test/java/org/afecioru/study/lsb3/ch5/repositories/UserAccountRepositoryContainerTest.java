package org.afecioru.study.lsb3.ch5.repositories;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.afecioru.study.lsb3.ch5.persistence.entities.UserAccountEntity;
import org.afecioru.study.lsb3.ch5.persistence.repositories.UserAccountRepository;

import static org.assertj.core.api.Assertions.*;
import static org.afecioru.study.lsb3.ch5.repositories.UserAccountRepositoryContainerTest.*;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = DataSourceInitializer.class)
public class UserAccountRepositoryContainerTest {

    @Autowired
    private UserAccountRepository repository;


    private static final List<UserAccountEntity> USERS = List.of(
        new UserAccountEntity("admin", "pass", "ROLE_ADMIN"),
        new UserAccountEntity("guest", "pass", "ROLE_USER")
    );

    @BeforeEach
    void beforeEach() {
        repository.saveAll(USERS);
    }

    @Test
    void fetchAllUsers() {
        // Arrange - do nothing

        // Act
        var users = repository.findAll();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users)
            .extracting(UserAccountEntity::getUsername)
            .containsExactlyInAnyOrder("admin", "guest");
    }

    @Test
    void fetchUserByUsername() {
        // Arrange

        // Act
        var user = repository.findByUsername("admin");

        // Assert
        assertThat(user)
            .extracting(UserAccountEntity::getUsername)
            .isEqualTo("admin");
    }


    // ----------[ Container setup ]---------
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "study";

    @Container
    static final PostgreSQLContainer<?> database =
        new PostgreSQLContainer<>("postgres:bullseye")
            .withUsername(DB_USER)
            .withPassword(DB_PASS);

    static class DataSourceInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                "spring.datasource.url=" + database.getJdbcUrl(),
                "spring.datasource.username=" + database.getUsername(),
                "spring.datasource.password=" + database.getPassword(),
                "spring.jpa.hibernate.ddl-auto=create-drop"
            );
        }
    }

}
