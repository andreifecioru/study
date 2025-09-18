package dev.afecioru.concurrency.threads;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class VirtualThreads {
    static final Logger logger = LoggerFactory.getLogger(VirtualThreads.class);

    static void log(String message) {
        logger.info("{} | {}", Thread.currentThread(), message);
    }

    @SneakyThrows
    static void sleep(Duration duration) {
        Thread.sleep(duration);
    }

    @SneakyThrows
    static void join(Thread thread) {
        thread.join();
    }

    static final Runnable task = () -> {
        log("Task is starting");
        sleep(Duration.ofSeconds(5));
        log("Task is finished");
    };

    static int noOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void main(String[] args) {
        log("Main application is started on " + noOfCores() + " cores");

        log("Starting the task ...");
        var thread = Thread.ofVirtual().name("Task #1")
            .start(task);

        join(thread);

        log("Main application is finished");
    }
}
