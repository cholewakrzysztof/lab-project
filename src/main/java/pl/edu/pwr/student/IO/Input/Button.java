package pl.edu.pwr.student.IO.Input;

public class Button extends SignalSender {
    public Button() {}

    public void press() {
        state = true;
        sendUpdate();
    }
    public void release() {
        state = false;
        sendUpdate();
    }
}
