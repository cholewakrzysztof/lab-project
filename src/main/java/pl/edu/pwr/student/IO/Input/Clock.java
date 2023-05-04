//package pl.edu.pwr.student.IO.Input;
//
//import pl.edu.pwr.student.Simulation;
//import pl.edu.pwr.student.UI.UiAvailable;
//
//public class Clock extends SignalSender implements Runnable, UiAvailable {
//    private long intervalOn;
//    private long intervalOff;
//
//    private final Thread thread;
//    private boolean power = false;
//
//    public void toggle() {
//        power = !power;
//
//        if (power)
//            thread.start();
//        else
//            thread.interrupt();
//    }
//
//    public void run() {
//        while (power) {
//            state = true;
//            sendUpdate();
//            Simulation.simWait(intervalOn);
//
//            state = false;
//            sendUpdate();
//            Simulation.simWait(intervalOff);
//        }
//    }
//
//    public Clock(long millisecondsOn, long millisecondsOff) {
//        intervalOn = millisecondsOn;
//        intervalOff = millisecondsOff;
//
//        thread = new Thread(this);
//    }
//
//    public void setIntervals(long millisecondsOn, long millisecondsOff) {
//        intervalOn = millisecondsOn;
//        intervalOff = millisecondsOff;
//    }
//}

package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

/**
 * Class that represents a clock logical element with configurable high and low times.
 */
public class Clock extends SignalSender implements Runnable, UiAvailable {
    /**
     * Creates a new clock element.
     * @param millisecondsOn the duration for which the clock will be on, in milliseconds.
     * @param millisecondsOff the duration for which the clock will be off, in milliseconds.
     */
    public Clock(long millisecondsOn, long millisecondsOff) {
        this.setIntervals(millisecondsOn, millisecondsOff);

        thread = new Thread(this);
    }

    /**
     * Duration for which the clock stays on during its every cycle.
     */
    private long intervalOn;
    /**
     * Duration for which the clock stays off during its every cycle.
     */
    private long intervalOff;

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
     * Changes the timings of this clock/
     *
     * @param millisecondsOn time the clock should be on - in milliseconds
     * @param millisecondsOff time the clock should be off - in milliseconds
     */
    public void setIntervals(long millisecondsOn, long millisecondsOff) {
        intervalOn = millisecondsOn;
        intervalOff = millisecondsOff;
    }

    /**
     * Switches the clock on and off.
     * NOT TO BE CALLED MANUALLY - Handled by the Thread class.
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