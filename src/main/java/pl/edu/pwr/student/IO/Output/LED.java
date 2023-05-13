package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.IO.UserUsable;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

/**
 * OBSOLETE - SHOULD BE CHANGED TO BE USED IN THE UI
 * OLD FUNCTIONALITY MOVED TO {@link DebugLED}
 */
public class LED extends BasicReceiver implements Runnable, UiAvailable, UserUsable {
    private final String name;
    private long milliseconds;
    private boolean power = false;
    private final Thread thread;

    public void toggle() {
        if (milliseconds < 1) {
            power = false;
            return;
        }

        power = !power;
        if (power)
            thread.start();
        else
            thread.interrupt();
    }
    public void run() {
        while (power) {
            System.out.println(name + ": " + state);
            Simulation.simWait(milliseconds);
        }
    }
    public void changeUpdateFreq(long updateMilliseconds) {
        milliseconds = updateMilliseconds;

        if (milliseconds < 1)
            toggle();
    }
    public LED(String name, long updateMilliseconds) {
        this.name = name;
        milliseconds = updateMilliseconds;

        thread = new Thread(this);
    }
    public void react() {}

    @Override
    public void connection(SignalReceiver receiver) {
    }
}
