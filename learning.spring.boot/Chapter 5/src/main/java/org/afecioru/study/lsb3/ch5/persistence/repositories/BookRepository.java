package org.afecioru.study.lsb3.ch5.persistence.repositories;

import org.afecioru.study.lsb3.ch5.persistence.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<BookEntity, Long> {
    BookEntity findByTitleAndAuthor(String title, String Author);
}
