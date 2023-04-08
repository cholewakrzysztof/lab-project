package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

public class Clock extends SignalSender implements Runnable, UiAvailable {
    private final long intervalOn;
    private final long intervalOff;

    private final Thread thread;
    private boolean power = false;

    public void toggle() {
        power = !power;

        if (power)
            thread.start();
        else
            thread.interrupt();
    }

    public void run() {
        while (power) {
            state = true;
            sendUpdate();
            Simulation.simWait(intervalOn);

            state = false;
            sendUpdate();
            Simulation.simWait(intervalOff);
        }
    }

    public Clock(long millisecondsOn, long millisecondsOff) {
        intervalOn = millisecondsOn;
        intervalOff = millisecondsOff;

        thread = new Thread(this);
    }

    @Override
    public void fullDisconnect() {
        disconnectOutputs();
    }
}
