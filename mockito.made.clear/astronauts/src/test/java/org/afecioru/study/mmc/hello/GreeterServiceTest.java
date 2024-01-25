package org.afecioru.study.mmc.hello;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.afecioru.study.mmc.hello.persistence.PersonRepository;
import org.afecioru.study.mmc.hello.services.GreeterService;
import org.afecioru.study.mmc.hello.services.TranslationService;
import org.afecioru.study.mmc.hello.persistence.entities.PersonEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class GreeterServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private GreeterService greeterService;

    @Test
    @DisplayName("Greet an existing person")
    void greetExistingPerson() {
        // Arrange
        var expected = "Hello, John Smith, from Mockito!";
        given(repository.findById(anyInt()))
            .willReturn(Optional.of(
                new PersonEntity(1, "John Smith")
            ));

        given(translationService.translate(expected, "en", "en"))
            .willReturn(expected);

        // Act
        var actual = greeterService.greet(1, "en", "en");

        // Assert
        assertThat(actual).isEqualTo(expected);

        var inOrder = inOrder(repository, translationService);
        inOrder.verify(repository).findById(anyInt());
        inOrder.verify(translationService)
            .translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    @DisplayName("Greet non-existing person")
    void greetNonExistingPerson() {
        // Arrange
        var expected = "Hello, World, from Mockito!";
        given(repository.findById(anyInt()))
            .willReturn(Optional.empty());
        given(translationService.translate(expected, "en", "en"))
            .willReturn(expected);

        // Act
        var actual = greeterService.greet(1, "en", "en");

        // Assert
        assertThat(actual).isEqualTo(expected);

        var inOrder = inOrder(repository, translationService);
        inOrder.verify(repository).findById(anyInt());
        inOrder.verify(translationService)
            .translate(anyString(), eq("en"), eq("en"));
    }
}
