package pl.edu.pwr.student.Gates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.HashSet;

public abstract class BasicGate extends SignalSender implements SignalReceiver, Compoundable {
    private final HashSet<SignalSender> inputs = new HashSet<>();
    protected abstract boolean checkState(HashSet<SignalSender> inputs);
    public boolean hasInputs() {
        return !inputs.isEmpty();
    }
    public void update() {
        if (inputs.isEmpty()) {
            state = false;
            return;
        }

        boolean newState = checkState(inputs);

        if (state == newState)
            return;

        state = newState;
        sendUpdate();
    }
    public boolean attemptConnect(SignalSender sender) {
        if (this == sender)
            return false;

        if (!sender.isConnected(this))
            return false;

        inputs.add(sender);
        return true;
    }
    public boolean attemptDisconnect(@NotNull SignalSender sender) {
        if (sender.isConnected(this))
            return false;

        inputs.remove(sender);
        return true;
    }
}
