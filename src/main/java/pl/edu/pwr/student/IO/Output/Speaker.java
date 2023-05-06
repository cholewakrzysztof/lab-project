package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.UI.UiAvailable;
import processing.core.PApplet;
import processing.sound.SinOsc;
import java.util.HashSet;

/**
 * Class that makes a sound as long as its state is on.
 */
public class Speaker extends BasicReceiver implements UiAvailable {
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
     * Connects speaker's output(there isn't one) to another object's input.
     *
     * @param receiver Object that would receive the output from the speaker
     */
    @Override
    public void connection(SignalReceiver receiver) {
    }

    /**
     * Method that stars and stops emitting sound based on the state of the input of this element.
     */
    protected void react() {
        if (state)
            sine.play();
        else
            sine.stop();
    }

    public void setFrequency(Integer frequency) {
        sine.freq(frequency);
    }
}
