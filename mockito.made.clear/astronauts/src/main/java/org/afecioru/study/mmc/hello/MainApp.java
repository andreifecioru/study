package org.afecioru.study.mmc.hello;

import org.afecioru.study.mmc.hello.persistence.entities.InMemoryPersonRepository;
import org.afecioru.study.mmc.hello.persistence.entities.PersonEntity;

public class MainApp {
    public static void main(String[] args) {
        var repository = new InMemoryPersonRepository();

        repository.save(new PersonEntity(1, "Andrei"));
        repository.save(new PersonEntity(2, "Radu"));
        repository.save(new PersonEntity(3, "Maria"));
        repository.save(new PersonEntity(4, "Alexandru"));

        repository
            .findAll()
            .forEach(System.out::println);
    }
}
