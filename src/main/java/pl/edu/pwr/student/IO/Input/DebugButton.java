package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;

/**
 * Debug class that combines switch and button functionalities into one and allows its use without the need of a GUI.
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
     * Changes the state the element and keeps it in that state for a specified amount of time.
     * @param milliseconds amount of time, in milliseconds, for which the button should stay change its state
     */
    public void press(long milliseconds) {
        this.milliseconds = milliseconds;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Changes the state of the button.
     * @return new state of the button
     */
    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }

    /**
     * Used when the button's state is changed temporarily.
     * NOT TO BE CALLED MANUALLY - Handled by the Thread class.
     */
    public void run() {
        toggle();
        Simulation.simWait(milliseconds);
        toggle();
    }
}
