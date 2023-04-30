package pl.edu.pwr.student.IO.Output;

/**
 * Light emitting diode class
 * Extends {@link BasicReceiver}.
 */
public class LED extends BasicReceiver {
    /**
     * Default constructor
     */
    public LED() {}

    /**
     * Method that makes the diode light up and turn off depending on it state
     */
    protected void react() {
        if (state) {
            // light up
        } else {
            // turn off
        }
    }
}
