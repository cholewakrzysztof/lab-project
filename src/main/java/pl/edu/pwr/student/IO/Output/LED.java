package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashSet;

public class LED extends BasicReceiver implements Runnable, UiAvailable {
    private final String name;
    private final long milliseconds;
    private boolean power = false;
    private final Thread thread;

    public boolean toggle() {
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
    public boolean getState() {
        return state;
    }

    @Override
    public int connection(SignalReceiver receiver) {
        return 0;
    }
}
