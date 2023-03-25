package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;
import processing.core.PApplet;
import processing.core.PVector;

public class Switch extends SignalSender implements Runnable {
    public Switch(String type, PApplet s, PVector v) {
        super(type, s, v);
    }

    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }
    private long milliseconds;
    public void press(long milliseconds) {
        if (state)
            return;

        this.milliseconds = milliseconds;
        Thread thread = new Thread(this);
        thread.start();
    }
    public void run() {
        super.run();
        toggle();
        Simulation.simWait(milliseconds);
        toggle();
    }
}
