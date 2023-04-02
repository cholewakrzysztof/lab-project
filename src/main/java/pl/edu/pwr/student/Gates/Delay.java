package pl.edu.pwr.student.Gates;

import pl.edu.pwr.student.IO.BasicPassThrough;
import pl.edu.pwr.student.Simulation;

public class Delay extends BasicPassThrough {
    private final long milliseconds;
    public boolean checkState(boolean input) {
        return input;
    }
    public void update() {
        Simulation.simWait(milliseconds);
        Thread thread = new Thread(this);
        thread.start();
    }
    public Delay(long delayMilliseconds) {
        milliseconds = delayMilliseconds;
    }
}
