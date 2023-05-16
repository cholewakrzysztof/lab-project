package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.HashSet;

/**
 * The UiAvailable interface defines methods for any element that can be placed on a canvas.
 * This interface is implemented by classes representing logic gates, and other components.
 */
public interface UiAvailable {
    /**
     * Returns all outputs from this element.
     *
     * @return a HashSet of SignalReceiver objects representing the outputs of this element.
     */
    default HashSet<SignalReceiver> getOutputs() {
        return new HashSet<>();
    }

    /**
     * Returns all inputs for this element.
     *
     * @return a HashSet of SignalSender objects representing the inputs of this element.
     */
    default HashSet<SignalSender> getInputs() {
        return new HashSet<>();
    }

    /**
     * Returns the current state of this element.
     *
     * @return true if the element is "on" or "active", false if it is "off" or "inactive".
     */
    default boolean getState(){
        return false;
    }

    /**
     * Disconnects all inputs from this element.
     * This is useful for resetting the state of the element.
     */
    void fullDisconnect();

    /**
     * Connects an input to this element.
     *
     * @param receiver the SignalReceiver object representing the input to be connected.
     */
    void connection(SignalReceiver receiver);
}
