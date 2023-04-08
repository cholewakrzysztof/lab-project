package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Input.SignalSender;
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
     * Gets all inputs from element
     */
    default HashSet<SignalSender> getInputs() {
        return new HashSet<SignalSender>();
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
