package src.IO.Output;

import src.IO.Input.SignalSender;

public interface SignalReceiver {
    boolean attemptConnect(SignalSender sender);
    boolean attemptDisconnect(SignalSender sender);
    void update();
}
