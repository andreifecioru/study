package org.afecioru.study.lsb3.ch5.persistence.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class BookEntityTest {

    @Test
    void newBookEntityNoArgsConstructor() {
        // Arrange - nothing to do

        // Act
        var bookEntity = new BookEntity();

        // Assert
        assertThat(bookEntity.getId()).isNull();
        assertThat(bookEntity.getTitle()).isNull();
        assertThat(bookEntity.getAuthor()).isNull();
        assertThat(bookEntity.getCopiesSold()).isNull();
    }

    @Test
    void newBookEntityShouldHaveNullId() {
        // Arrange - nothing to do

        // Act
        var bookEntity = new BookEntity("Test book", "Test author", 1000);

        // Assert
        assertThat(bookEntity.getId()).isNull();
        assertThat(bookEntity.getTitle()).isEqualTo("Test book");
        assertThat(bookEntity.getAuthor()).isEqualTo("Test author");
        assertThat(bookEntity.getCopiesSold()).isEqualTo(1000);
    }

    @Test
    void bookStringRepresentation() {
        // Arrange
        var expected = "BookEntity(id=null, title=\"Test book\", author=\"Test author\", copiesSold=1,000)";
        var bookEntity = new BookEntity("Test book", "Test author", 1000);

        // Act
        var actual = bookEntity.toString();

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void settersShouldMutateState() {
        // Arrange
        var bookEntity = new BookEntity("Test book", "Test author", 1000);

        // Act
        bookEntity.setId(1L);
        bookEntity.setTitle("Mutated test title");
        bookEntity.setAuthor("Mutated test author");
        bookEntity.setCopiesSold(2000);

        // Assert
        assertThat(bookEntity.getId()).isEqualTo(1L);
        assertThat(bookEntity.getTitle()).isEqualTo("Mutated test title");
        assertThat(bookEntity.getAuthor()).isEqualTo("Mutated test author");
        assertThat(bookEntity.getCopiesSold()).isEqualTo(2000);
    }
}
