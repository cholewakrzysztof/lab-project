package pl.edu.pwr.student.IO;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

public class VirtualIO extends SignalSender implements SignalReceiver, Compoundable {
    public final String name;
    private SignalSender input;

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
    public void update() {
        if (input == null) {
            state = false;
            return;
        }

        state = input.getState();
        sendUpdate();
    }
    public boolean hasInputs() {
        return input != null;
    }
    public boolean isIO() {
        return true;
    }

    public VirtualIO(String name) {
        this.name = name;
    }
    public Compoundable getNewInstance() {
        return new VirtualIO(name);
    }
}
