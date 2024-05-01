package ch_02;

public class CheckSysDeps {
    static boolean isSwingAvailable() {
        try {
            Class.forName("javax.swing.Button");
            System.out.println("Swing is available");
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Swing not available.");
            return false;
        }
    }
}
