package pl.edu.pwr.student.IO.Input;

public class Switch extends SignalSender {
    public Switch() {}

    public boolean toggle() {
        state = !state;
        sendUpdate();
        return state;
    }
}
