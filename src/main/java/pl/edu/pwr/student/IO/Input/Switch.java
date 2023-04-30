package pl.edu.pwr.student.IO.Input;

/**
 * Class representing the switch element.
 * Extends {@link SignalSender}
 */
public class Switch extends SignalSender {
    /**
     * Default constructor.
     */
    public Switch() {}

    /**
     * Method that changes the state of the switch.
     * @return new state of the switch
     */
    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }
}
