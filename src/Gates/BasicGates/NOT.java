package src.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import src.Gates.Compoundable;
import src.IO.Input.SignalSender;
import src.IO.Output.SignalReceiver;

public class NOT extends SignalSender implements SignalReceiver, Compoundable {
    private SignalSender input = null;

    public boolean hasInputs() {
        return input != null;
    }
    public void update() {
        if (input == null) {
            state = false;
            return;
        }

        state = !input.getState();
        sendUpdate();
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
}
