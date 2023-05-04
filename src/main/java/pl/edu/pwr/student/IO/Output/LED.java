package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashSet;

/**
 * OBSOLETE - SHOULD BE CHANGED TO BE USED IN THE UI
 * OLD FUNCTIONALITY MOVED TO {@link DebugLED}
 */
public class LED extends BasicReceiver implements Runnable, UiAvailable {
    private final String name;
    private long milliseconds;
    private boolean power = false;
    private final Thread thread;

    public boolean toggle() {
        if (milliseconds < 1) {
            power = false;
            return false;
        }

        power = !power;
        if (power)
            thread.start();
        else
            thread.interrupt();
        return power;
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
    public HashSet<SignalReceiver> getOutputs() {
        return UiAvailable.super.getOutputs();
    }

    @Override
    public int connection(SignalReceiver receiver) {
        return 0;
    }
}
