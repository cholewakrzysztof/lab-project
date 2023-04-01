package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.IO.Input.SignalSender;

public interface SignalReceiver {
    boolean attemptConnect(SignalSender sender);
    boolean attemptDisconnect(SignalSender sender);
    void update();
    void disconnectInputs();
}
