package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;

/**
 * Class that represents a clock logical element with configurable high and low times.
 * Extends {@link SignalSender} and implements Runnable.
 */
public class Clock extends SignalSender implements Runnable {
    /**
     * Creates a new clock element.
     * @param millisecondsOn the duration for which the clock will be on, in milliseconds.
     * @param millisecondsOff the duration for which the clock will be off, in milliseconds.
     */
    public Clock(long millisecondsOn, long millisecondsOff) {
        intervalOn = millisecondsOn;
        intervalOff = millisecondsOff;

        thread = new Thread(this);
    }

    /**
     * Duration for which the clock stays on during its every cycle.
     */
    private final long intervalOn;
    /**
     * Duration for which the clock stays off during its every cycle.
     */
    private final long intervalOff;

    /**
     * The thread on which the clock runs.
     */
    private final Thread thread;
    /**
     * Whether the clock is currently running or not.
     */
    private boolean power = false;

    /**
     * Toggle the power state of the clock.
     * @return the power state of the clock after being toggled.
     */
    public boolean toggle() {
        power = !power;

        if (power)
            thread.start();

        return power;
    }

    /**
     * Method that switches the clock on and off.
     */
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
}
