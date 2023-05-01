package pl.edu.pwr.student.Gates;

import pl.edu.pwr.student.IO.BasicPassThrough;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

public class Delay extends BasicPassThrough implements UiAvailable {
    private long milliseconds;
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

    public void setDelay(long delayMilliseconds) {
        milliseconds = delayMilliseconds;
    }
}
