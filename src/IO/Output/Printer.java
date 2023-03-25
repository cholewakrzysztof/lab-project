package src.IO.Output;

import org.jetbrains.annotations.NotNull;
import src.IO.Input.SignalSender;

public class Printer implements SignalReceiver {
    private SignalSender input = null;
    private boolean state = false;
    private final String name;

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
        print();
    }
    private void print() {
        System.out.println(name + " state changed to: " + state);
    }
    public Printer(String name) {
        this.name = name;
    }
}
