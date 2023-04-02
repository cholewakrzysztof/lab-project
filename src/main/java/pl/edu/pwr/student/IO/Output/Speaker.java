package pl.edu.pwr.student.IO.Output;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;
import processing.core.PApplet;
import processing.sound.SinOsc;

import java.util.HashSet;

public class Speaker extends PApplet implements SignalReceiver, Runnable, UiAvailable {
    private SignalSender input = null;
    private boolean state = false;

    private final String name;
    private final long milliseconds;
    private boolean power = false;
    private final Thread thread;

    private final SinOsc sine;


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
        if (input == null)
            state = false;
        else
            state = input.getState();
    }

    public void run() {
        while (power) {
            System.out.println(name + ": " + state);
            if (state)
                sine.play();
            else
                sine.stop();
            Simulation.simWait(milliseconds);
        }
        sine.stop();
    }

    public boolean toggle() {
        power = !power;
        if (power)
            thread.start();
        else {
            thread.interrupt();
            sine.stop();
        }
        return power;
    }

    @Override
    public HashSet<SignalReceiver> getOutputs() {
        return UiAvailable.super.getOutputs();
    }

    @Override
    public boolean getState() {
        return state;
    }

    public void fullDisconnect() {
        disconnectInputs();
    }

    @Override
    public int connection(SignalReceiver receiver) {
        return 0;
    }

    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }

    public Speaker(String name, long updateMilliseconds) {
        this.name = name;
        milliseconds = updateMilliseconds;

        sine = new SinOsc(this);
        sine.amp(0.1f);
        sine.freq(250);

        thread = new Thread(this);
    }
}
