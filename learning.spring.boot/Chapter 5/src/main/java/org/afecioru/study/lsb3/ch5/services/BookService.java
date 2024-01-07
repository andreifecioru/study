package org.afecioru.study.lsb3.ch5.services;

import java.util.List;

import lombok.Data;
import org.afecioru.study.lsb3.ch5.persistence.repositories.BookRepository;
import org.springframework.stereotype.Service;

import org.afecioru.study.lsb3.ch5.persistence.entities.BookEntity;
import org.afecioru.study.lsb3.ch5.controllers.dtos.BookDTO;


@Data
@Service
public class BookService {
    private final BookRepository repository;

    public List<BookDTO> allBooks() {
        return repository.findAll().stream()
            .map(BookService::dtoFromEntity)
            .toList();
    }

    public BookDTO addBook(BookDTO bookDTO) {
        var bookEntity = repository.save(entityFromDTO(bookDTO));
        return dtoFromEntity(bookEntity);
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    static BookDTO dtoFromEntity(BookEntity bookEntity) {
        return new BookDTO(
            bookEntity.getId(),
            bookEntity.getTitle(),
            bookEntity.getAuthor(),
            bookEntity.getCopiesSold()
        );
    }

    static BookEntity entityFromDTO(BookDTO bookDTO) {
        return new BookEntity(
            bookDTO.id(),
            bookDTO.title(),
            bookDTO.author(),
            bookDTO.copiesSold()
        );
    }
}
