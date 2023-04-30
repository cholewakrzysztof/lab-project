package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;

/**
 * Debug class that combines switch and button functionalities into one and allows its use without the need of a GUI.
 * Extends {@link SignalSender} and implements Runnable.
 */
public class DebugButton extends SignalSender implements Runnable {
    /**
     * Default constructor.
     */
    public DebugButton() {}

    /**
     * Time to stay pressed.
     */
    private long milliseconds;

    /**
     * Method that changes the state the element and keeps it in that state for a specified amount of time.
     * @param milliseconds amount of time, in milliseconds, for which the button should stay change its state
     */
    public void press(long milliseconds) {
        this.milliseconds = milliseconds;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Method that changes the state of the button.
     * @return new state of the button
     */
    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }

    /**
     * Method that is used when the button's state is changed temporarily.
     */
    public void run() {
        toggle();
        Simulation.simWait(milliseconds);
        toggle();
    }
}
