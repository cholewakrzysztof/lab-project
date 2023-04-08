package pl.edu.pwr.student.IO.Output;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

public abstract class BasicReceiver implements SignalReceiver {
    protected SignalSender input = null;
    protected boolean state = false;

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
    public void fullDisconnect() {
        disconnectInputs();
    }
    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }
    public void update() {
        boolean newState = false;

        if (input != null)
            newState = input.getState();

        if (state == newState)
            return;

        state = newState;
        react();
    }
    protected void react() {}
    public boolean getState() {
        return state;
    }

    public HashSet<SignalSender> getInputs() {
        HashSet<SignalSender> temp = new HashSet<SignalSender>();
        temp.add(input);
        return temp;
    }
}
