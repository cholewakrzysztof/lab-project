package pl.edu.pwr.student.IO;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

public abstract class BasicPassThrough extends SignalSender implements SignalReceiver, Compoundable, Runnable {
    private SignalSender input;
    public boolean hasInputs() {
        return input != null;
    }
    public void run() {
        boolean oldState = state;
        if (input != null)
            state = checkState(input.getState());

        if (oldState != state)
            sendUpdate();
    }
    public void update() {
        Thread thread = new Thread(this);
        thread.start();
    }
    public boolean attemptConnect(SignalSender sender) {
        if (input != null)
            return false;

        if (!sender.isConnected(this))
            return false;

        input = sender;
        return true;
    }
    public boolean attemptDisconnect(@NotNull SignalSender sender) {
        if (input != sender)
            return false;

        if (sender.isConnected(this))
            return false;

        input = null;
        return true;
    }
    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }
    public void fullDisconnect() {
        super.disconnectOutputs();
        disconnectInputs();
    }
    protected abstract boolean checkState(boolean inputState);
}
