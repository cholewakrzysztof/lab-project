package pl.edu.pwr.student.IO.Output;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;

public class LED extends UiElement implements SignalReceiver, Runnable {
    private SignalSender input = null;
    private boolean state = false;
    private final String name;
    private final long milliseconds;
    private boolean power = false;

    public boolean toggle() {
        power = !power;
        return power;
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
    public void update() {
        if (input == null) {
            state = false;
            return;
        }

        state = input.getState();
    }
    private void print() {
        System.out.println(name + ": " + state);
    }
    public void run() {
        super.run();

        while (power) {
            print();
            Simulation.simWait(milliseconds);
        }
    }
    public LED(String name, long updateMilliseconds, PApplet s, PVector v) {
        super("AND", s, v);
        this.name = name;
        milliseconds = updateMilliseconds;

        Thread thread = new Thread(this);
        thread.start();
    }
}
