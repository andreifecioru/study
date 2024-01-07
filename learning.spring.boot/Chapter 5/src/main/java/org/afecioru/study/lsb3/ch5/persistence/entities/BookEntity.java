package org.afecioru.study.lsb3.ch5.persistence.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.MessageFormat;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;

    String author;

    Integer copiesSold;

    public BookEntity(String title, String author, int copiesSold) {
        this(null, title, author, copiesSold);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
            "BookEntity(id={0}, title=\"{1}\", author=\"{2}\", copiesSold={3,number,integer})",
            id, title, author, copiesSold
        );
    }


}
