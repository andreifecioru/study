package org.afecioru.study.lsb3.ch5.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.afecioru.study.lsb3.ch5.persistence.repositories.BookRepository;
import org.afecioru.study.lsb3.ch5.controllers.dtos.BookDTO;
import org.afecioru.study.lsb3.ch5.persistence.entities.BookEntity;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public final class BookServiceTest {

    private BookService service;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void beforeEach() {
        service = new BookService(repository);
    }

    @Test
    void fetchAllBooks() {
        // Arrange
        var books = List.of(
            new BookEntity(1L, "Book One", "Author One", 100),
            new BookEntity(2L, "Book Two", "Author Two", 200),
            new BookEntity(3L, "Book Three", "Author One", 300)
        );
        var expected = books.stream().map(BookService::dtoFromEntity).toList();

        given(repository.findAll()).willReturn(books);

        // Act
        var actual = service.allBooks();

        // Assert
        assertThat(actual)
            .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void addBook() {
        // Arrange
        var bookDTO = new BookDTO(1L, "New Book", "New Author", 100);
        var bookEntity = BookService.entityFromDTO(bookDTO);
        given(repository.save(any(BookEntity.class)))
            .willReturn(bookEntity);

        // Act
        var actual = service.addBook(bookDTO);

        // Assert
        assertThat(actual).isEqualTo(bookDTO);
    }

    @Test
    void deleteBook() {
        // Arrange - nothing to do

        // Act
        service.deleteBook(1L);

        // Assert
        verify(repository).deleteById(1L);
    }
}
