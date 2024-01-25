package org.afecioru.study.mmc.hello.persistence.entities;

import org.afecioru.study.mmc.hello.persistence.PersonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPersonRepository implements PersonRepository {
    private final Map<Integer, PersonEntity> storage = new HashMap<>();

    @Override
    public PersonEntity save(PersonEntity person) {
        return storage.put(person.id(), person);
    }

    @Override
    public Optional<PersonEntity> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<PersonEntity> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void delete(PersonEntity person) {
        storage.remove(person.id());
    }
}
