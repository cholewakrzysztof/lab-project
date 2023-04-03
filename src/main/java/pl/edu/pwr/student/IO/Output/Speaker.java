package pl.edu.pwr.student.IO.Output;

import processing.core.PApplet;
import processing.sound.SinOsc;

public class Speaker extends BasicReceiver implements Runnable {
    private boolean power = false;
    private final Thread thread;

    private final SinOsc sine;

    public void run() {
        while (power) {
            if (state)
                sine.play();
            else
                sine.stop();
        }
        sine.stop();
    }
    public boolean toggle() {
        power = !power;

        if (power)
            thread.start();
        else {
            thread.interrupt();
            sine.stop();
        }

        return power;
    }
    public Speaker() {
        thread = new Thread(this);

        sine = new SinOsc(new PApplet());
        sine.amp(0.1f);
        sine.freq(250);
    }
    protected void react() {}
}
