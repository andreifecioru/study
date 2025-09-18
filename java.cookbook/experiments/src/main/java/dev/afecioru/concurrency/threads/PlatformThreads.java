package dev.afecioru.concurrency.threads;

import lombok.SneakyThrows;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlatformThreads {
    static final Logger logger = LoggerFactory.getLogger(PlatformThreads.class);

    static void log(String message) {
        logger.info("{} | {}", Thread.currentThread(), message);
    }

    @SneakyThrows
    static void sleep(long millis) {
        Thread.sleep(millis);
    }

    public static void main(String[] args) {
        val aThread = new Thread(() -> {
            log("Daemon thread is starting");
            sleep(5000);
            log("Daemon thread is finished");
        });
        log("Main application is started");

        log("Starting the thread...");
        // aThread.setDaemon(false);
        aThread.start();

        log("Main application is finished");
    }
}
