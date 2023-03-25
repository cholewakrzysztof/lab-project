package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;

public abstract class SignalSender extends UiElement {
    private final HashSet<SignalReceiver> outputs = new HashSet<>();
    protected boolean state = false;

    public SignalSender(String type, PApplet s, PVector v) {
        super(type, s, v);
    }

    public boolean getState() {
        return state;
    }
    protected void sendUpdate() {
        for (SignalReceiver output : outputs)
            output.update();
    }

    public int connection(SignalReceiver receiver) {
        if (this == receiver)
            return 0;

        if (outputs.contains(receiver)) {
            outputs.remove(receiver);
            if (receiver.attemptDisconnect(this)) {
                receiver.update();
                return 2;
            }
            outputs.add(receiver);
        } else {
            outputs.add(receiver);
            if (receiver.attemptConnect(this)) {
                receiver.update();
                return 1;
            }
            outputs.remove(receiver);
        }
        return 0;
    }
    public boolean isConnected(SignalReceiver receiver) {
        return outputs.contains(receiver);
    }
    public HashSet<SignalReceiver> getOutputs() {
        return new HashSet<>(outputs);
    }
}
