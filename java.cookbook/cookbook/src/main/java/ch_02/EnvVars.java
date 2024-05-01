package ch_02;

public class EnvVars {
    static String getUserHomeDir() {
        return System.getenv("HOME");
    }
}
