package extras;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import static extras.StringSerialization.Person;


public class StringSerializationTest {

    @Test
    @DisplayName("Deserialization after serialization produces the original input")
    void testSerializationAndDeserialization() throws IOException, ClassNotFoundException {
        // Arrange
        var original = new Person("John Smith", 42);

        // Act
        var serialized = StringSerialization.serialize(original);
        var deserialized = (Person) StringSerialization.deserialize(serialized);

        // Assert
        assertThat(deserialized).isEqualTo(original);
    }
}
