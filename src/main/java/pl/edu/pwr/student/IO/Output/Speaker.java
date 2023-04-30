package pl.edu.pwr.student.IO.Output;

import processing.core.PApplet;
import processing.sound.SinOsc;

/**
 * Class that makes a sound as long as its state is on.
 * Extends {@link BasicReceiver}.
 */
public class Speaker extends BasicReceiver {
    /**
     * Default constructor.
     */
    public Speaker() {
        sine = new SinOsc(new PApplet());
        sine.amp(0.1f);
        sine.freq(250);
    }

    /**
     * Variable that stores the emitted sound configuration.
     */
    private final SinOsc sine;

    /**
     * Method that stars and stops emitting sound based on the state of the input of this element.
     */
    protected void react() {
        if (state)
            sine.play();
        else
            sine.stop();
    }
}
