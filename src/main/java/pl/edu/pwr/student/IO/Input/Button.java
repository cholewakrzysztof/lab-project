package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.IO.UserUsable;

/**
 * Represents a button that a user can press.
 */
public class Button extends SignalSender implements UserUsable {
    /**
     * Default constructor
     */
    public Button() {}

    /**
     * Called when user presses the button.
     * Changes the state to true and sends an update to all the outputs of this element({@link pl.edu.pwr.student.IO.Output.SignalReceiver}).
     */
    public void press() {
        state = true;
        sendUpdate();
    }

    /**
     * Called when user stops pressing the button.
     * Changes the state to false and updates all the outputs of this element({@link pl.edu.pwr.student.IO.Output.SignalReceiver}).
     */
    public void release() {
        state = false;
        sendUpdate();
    }
}
