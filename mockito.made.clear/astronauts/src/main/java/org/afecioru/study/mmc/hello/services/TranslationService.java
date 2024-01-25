package org.afecioru.study.mmc.hello.services;

public interface TranslationService {
    default String translate(String input, String sourceLang, String targetLang) {
        return input;
    }
}
