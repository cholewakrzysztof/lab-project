package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;
import processing.core.PApplet;
import processing.core.PVector;

public class Clock extends SignalSender implements Runnable {
    private final long intervalOn;
    private final long intervalOff;

    private final Thread thread = new Thread(this);
    private boolean run = false;

    public void toggle() {
        run = !run;

        if (run)
            thread.start();
        else
            thread.interrupt();
    }

    public void run() {
        super.run();
        while (run) {
            Simulation.simWait(intervalOff);
            state = true;

            Simulation.simWait(intervalOn);
            state = false;
        }
    }

    public Clock(long millisecondsOn, long millisecondsOff, String type, PApplet s, PVector v) {
        super(type, s, v);
        intervalOn = millisecondsOn;
        intervalOff = millisecondsOff;
    }
}
