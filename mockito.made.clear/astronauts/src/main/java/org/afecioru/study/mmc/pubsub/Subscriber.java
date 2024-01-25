package org.afecioru.study.mmc.pubsub;


public record Subscriber(int id) {
    void receive(String message) {
        System.out.printf(message);
    }
}
