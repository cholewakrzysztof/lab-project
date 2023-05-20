package pl.edu.pwr.student;

import pl.edu.pwr.student.UI.Canvas;
import uibooster.UiBooster;

/**
 * Main class of the application
 */
public class Simulation {

    /**
     * Private constructor to prevent creating instances of this class.
     */
    private Simulation(){}

    /**
     * Static method that calls a try-catch block with Thread.sleep().
     * Created so that there's no need to create a try-catch every time you want a thread to wait.
     *
     * @param milliseconds - time to wait in milliseconds
     */
    public static void simWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Main method of the application.
     * @param args - arguments passed to the application
     */
    public static void main(String[] args) {
        try {
            new Canvas();
        } catch (Exception e) {
            new UiBooster().showException(
                    "An error occurred",
                    "Exception message",
                    e
            );
        }
    }
}
