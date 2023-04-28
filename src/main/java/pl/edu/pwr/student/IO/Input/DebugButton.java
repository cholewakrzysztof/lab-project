package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;

public class DebugButton extends SignalSender implements Runnable {
    public DebugButton() {}

    private long milliseconds;
    public void press(long milliseconds) {
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
