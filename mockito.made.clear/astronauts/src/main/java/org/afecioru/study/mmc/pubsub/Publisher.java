package org.afecioru.study.mmc.pubsub;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


public class Publisher {
    private final Map<Integer, Subscriber> subscribers;
    private final Formatter formatter;

    public Publisher() {
        subscribers = new HashMap<>();
        formatter = new DefaultFormatter();
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.put(subscriber.id(), subscriber);
    }

    public void send(String message, boolean formatted) {
        subscribers.forEach((id, subscriber) -> {
            try {
                var preProcessedMessage = PreProcessor.preProcess(message);
                var messageToSend = formatted
                    ? MessageFormat.format(formatter.format(), subscriber.id(), preProcessedMessage)
                    : preProcessedMessage;
                subscriber.receive(messageToSend);
            } catch (Exception e) {
                // do nothing
            }
        });
    }

    public void send(String message) {
        send(message, false);
    }
}
