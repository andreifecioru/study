package org.afecioru.study.mmc.hello.persistence;

import java.util.List;
import java.util.Optional;

import org.afecioru.study.mmc.hello.persistence.entities.PersonEntity;


public interface PersonRepository {
    PersonEntity save(PersonEntity person);

    Optional<PersonEntity> findById(int id);

    List<PersonEntity> findAll();

    long count();

    void delete(PersonEntity person);
}
