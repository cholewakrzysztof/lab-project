package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.IO.UserUsable;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

/**
 * OBSOLETE - SHOULD BE CHANGED TO BE USED IN THE UI
 * OLD FUNCTIONALITY MOVED TO {@link DebugButton}
 */
public class Switch extends SignalSender implements Runnable, UiAvailable, UserUsable {
    public Switch() {}
    private long milliseconds;
    public void press(long milliseconds) {
        if (state)
            return;

        this.milliseconds = milliseconds;
        Thread thread = new Thread(this);
        thread.start();
    }
    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }
    public void run() {
        toggle();
        Simulation.simWait(milliseconds);
        toggle();
    }
}
