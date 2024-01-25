package org.afecioru.study.mmc.hello.services;

import lombok.Data;

import org.afecioru.study.mmc.hello.persistence.entities.PersonEntity;
import org.afecioru.study.mmc.hello.persistence.PersonRepository;

@Data
public class GreeterService {
    static private final String greeting = "Hello, %s, from Mockito!";

    // Dependencies
    private final PersonRepository personRepository;
    private final TranslationService translationService;

    public String greet(int id, String sourceLang, String targetLang) {
        var name = personRepository.findById(id)
            .map(PersonEntity::name)
            .orElse("World");

        return translationService.translate(
            String.format(greeting, name), sourceLang, targetLang
        );
    }
}
