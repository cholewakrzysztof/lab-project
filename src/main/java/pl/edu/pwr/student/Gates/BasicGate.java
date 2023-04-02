package pl.edu.pwr.student.Gates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.HashSet;

public abstract class BasicGate extends SignalSender implements SignalReceiver, Compoundable, Runnable {
    private final HashSet<SignalSender> inputs = new HashSet<>();
    protected abstract boolean checkState(HashSet<SignalSender> inputs);

    public boolean hasInputs() {
        return !inputs.isEmpty();
    }
    public void run() {
        boolean oldState = state;
        if (!inputs.isEmpty())
            state = checkState(inputs);

        if (oldState != state)
            sendUpdate();
    }
    public void update() {
        Thread thread = new Thread(this);
        thread.start();
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

    public void disconnectInputs() {
        HashSet<SignalSender> tempInputs = new HashSet<>(inputs);
        for (SignalSender input : tempInputs)
            input.connection(this);
        if (!inputs.isEmpty())
            throw new RuntimeException("Error disconnecting inputs");
    }
    public void fullDisconnect() {
        super.disconnectOutputs();
        this.disconnectInputs();
    }

    public BasicGate() {}
}
