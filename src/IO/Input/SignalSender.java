package src.IO.Input;

import src.IO.Output.SignalReceiver;

import java.util.HashSet;

public abstract class SignalSender {
    private final HashSet<SignalReceiver> outputs = new HashSet<>();
    protected boolean state = false;

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
