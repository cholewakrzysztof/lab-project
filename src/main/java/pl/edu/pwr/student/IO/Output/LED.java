package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.Simulation;

public class LED extends BasicReceiver implements Runnable {
    private final String name;
    private final long milliseconds;
    private boolean power = false;
    private final Thread thread;

    public boolean toggle() {
        power = !power;
        if (power)
            thread.start();
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
}
