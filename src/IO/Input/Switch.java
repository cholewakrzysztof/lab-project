package src.IO.Input;

import src.Simulation;

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
