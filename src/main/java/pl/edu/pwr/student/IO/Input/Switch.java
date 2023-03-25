package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.Simulation;

public class Switch extends SignalSender implements Runnable {
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
        toggle();
        Simulation.simWait(milliseconds);
        toggle();
    }
}
