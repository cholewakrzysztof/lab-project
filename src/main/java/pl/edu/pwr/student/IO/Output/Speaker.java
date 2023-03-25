package pl.edu.pwr.student.IO.Output;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SinOsc;

public class Speaker extends UiElement implements SignalReceiver, Runnable {
    private SignalSender input = null;
    private boolean state = false;
    private final String name;
    private final long milliseconds;
    private boolean power = false;

    private SinOsc sine;

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
            if (state){
                sine.play();
            } else {
                sine.stop();
            }
        }
    }
    public Speaker(String name, long updateMilliseconds, String type, PApplet s, PVector v) {
        super(type, s, v);
        this.name = name;
        milliseconds = updateMilliseconds;

        sine = new SinOsc(this);
        sine.amp(0.1f);
        sine.freq(250);

        Thread thread = new Thread(this);
        thread.start();
    }
}
