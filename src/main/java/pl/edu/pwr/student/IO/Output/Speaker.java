package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.UI.UiAvailable;
import processing.core.PApplet;
import processing.sound.SinOsc;
import java.util.HashSet;

public class Speaker extends BasicReceiver implements UiAvailable {
    private final SinOsc sine;

    protected void react() {
        if (state)
            sine.play();
        else
            sine.stop();
    }

    public Speaker() {
        sine = new SinOsc(new PApplet());
    }
    @Override
    public HashSet<SignalReceiver> getOutputs() {
        return UiAvailable.super.getOutputs();
    }

    public void fullDisconnect() {
        disconnectInputs();
    }

    @Override
    public int connection(SignalReceiver receiver) {
        return 0;
    }

    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }

    public void setFrequency(Integer frequency) {
        sine.freq(frequency);
    }
}
