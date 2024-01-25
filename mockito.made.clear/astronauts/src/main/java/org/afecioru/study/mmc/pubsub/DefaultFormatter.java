package org.afecioru.study.mmc.pubsub;

public class DefaultFormatter implements Formatter {
    @Override
    public String format() {
        return "[SUB {0,number,integer}]: {1}";
    }
}
