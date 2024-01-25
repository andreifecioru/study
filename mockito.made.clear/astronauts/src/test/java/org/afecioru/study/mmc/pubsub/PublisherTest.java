package org.afecioru.study.mmc.pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class PublisherTest {

    private Publisher publisher = new Publisher();

    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @Captor
    private ArgumentCaptor<String> stringArg;

    @BeforeEach
    void beforeEach() {
        publisher = new Publisher();

        when(sub1.id()).thenReturn(1);
        when(sub2.id()).thenReturn(2);

        publisher.addSubscriber(sub1);
        publisher.addSubscriber(sub2);
    }

    @Test
    @DisplayName("Publishing a message to multiple subscribers")
    void publishToMultipleSubscribers() {
        // Arrange
        var testMessage = "test message";
        doThrow(new RuntimeException("Subscriber failure")).when(sub1).receive(anyString());

        // Act
        publisher.send(testMessage);

        // Assert
        // Even if sub1 throws an exception, we expect both subs to be invoked
        var inOrder = inOrder(sub1, sub2);

        inOrder.verify(sub1).receive(stringArg.capture());
        assertThat(stringArg.getValue()).isEqualTo(testMessage.toUpperCase());

        inOrder.verify(sub2).receive(anyString());

        // We expect to invoke id() only once for non-formatted messages (when registering the subscriber)
        verify(sub1).id();
        verify(sub2).id();
    }

    @Test
    @DisplayName("Publishing formatted messages")
    void publishFormattedMessage() {
        // Arrange
        var testMessage = "test message";
        var expected = MessageFormat.format(
            new DefaultFormatter().format(), 1, testMessage.toUpperCase()
        );

        // Act
        publisher.send(testMessage, true);

        // Assert
        verify(sub1).receive(argThat(msg -> msg.equals(expected)));

        // We expect to invoke id() twice for formatted messages
        //   - when registering the subscriber
        //   - when formatting the message before publishing
        verify(sub1, times(2)).id();
        verify(sub2, times(2)).id();
    }

    @Test
    @DisplayName("Mocking a static method")
    void mockingStaticMethod() {
        try (var mocked = mockStatic(PreProcessor.class)) {
            // Arrange
            mocked.when(() -> PreProcessor.preProcess(anyString()))
                .thenAnswer(invocation -> {
                    var message = invocation.getArgument(0, String.class);
                    var messageLength = message.length();
                    return String.format("%s (len %d)", invocation.getArgument(0), messageLength);
                });

            var message = "test message";
            var expected = "test message (len 12)";

            // Act
            publisher.send(message);

            // Assert
            verify(sub1).receive(argThat(msg -> msg.equals(expected)));
            verify(sub2).receive(argThat(msg -> msg.equals(expected)));
            mocked.verify(() -> PreProcessor.preProcess(anyString()), times(2));
        }
    }

    @Test
    @DisplayName("Mocking a constructor")
    void mockingConstructors() {
        try (var ignored = mockConstruction(DefaultFormatter.class,
            (mock, context) -> when(mock.format()).thenReturn("<{0}> {1}")
            )) {

            // Arrange
            var message = "test message";
            publisher = new Publisher();
            publisher.addSubscriber(sub1);

            // Act
            publisher.send(message, true);

            // Assert
            verify(sub1).receive(argThat(msg -> msg.equals("<1> TEST MESSAGE")));
        }
    }
}
