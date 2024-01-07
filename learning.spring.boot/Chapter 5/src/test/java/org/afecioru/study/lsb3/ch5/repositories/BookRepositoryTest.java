package org.afecioru.study.lsb3.ch5.repositories;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.afecioru.study.lsb3.ch5.persistence.entities.BookEntity;
import org.afecioru.study.lsb3.ch5.persistence.repositories.BookRepository;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    private static final List<BookEntity> BOOKS = List.of(
        new BookEntity("Book One", "Author One", 100),
        new BookEntity("Book Two", "Author Two", 200),
        new BookEntity("Book Three", "Author Three", 300)
    );

    @BeforeEach
    void beforeEach() {
        repository.saveAll(BOOKS);
    }

    @Test
    void findAllBooks() {
        // Arrange - nothing to do

        // Act
        var books = repository.findAll();

        // Assert
        assertThat(books).hasSize(3);
        assertThat(books)
            .extracting(BookEntity::getTitle)
            .containsExactlyInAnyOrder(
                "Book One", "Book Two", "Book Three"
            );
    }

    @Test
    void findByTitleAndAuthor() {
        // Arrange - do nothing

        // Act
        var book = repository.findByTitleAndAuthor("Book One", "Author One");

        // Assert
        assertThat(book).extracting(BookEntity::getTitle).isEqualTo("Book One");
        assertThat(book).extracting(BookEntity::getAuthor).isEqualTo("Author One");
    }
}
