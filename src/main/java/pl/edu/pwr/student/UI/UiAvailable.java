package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.HashSet;

public interface UiAvailable {
    /**
     * Gets all outputs from element
     */
    default HashSet<SignalReceiver> getOutputs() {
        return new HashSet<SignalReceiver>();
    }

    /**
     * Gets state of element
     */
    boolean getState();

    /**
     * Gets if element has inputs
     */
    void fullDisconnect();

    /**
     * Gets if element has inputs
     */
    int connection(SignalReceiver receiver);
}
