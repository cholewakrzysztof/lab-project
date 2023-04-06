package pl.edu.pwr.student.IO.Output;

import processing.core.PApplet;
import processing.sound.SinOsc;

public class Speaker extends BasicReceiver {
    private final SinOsc sine;

    protected void react() {
        if (state)
            sine.play();
        else
            sine.stop();
    }

    public Speaker() {
        sine = new SinOsc(new PApplet());
        sine.amp(0.1f);
        sine.freq(250);
    }
}
