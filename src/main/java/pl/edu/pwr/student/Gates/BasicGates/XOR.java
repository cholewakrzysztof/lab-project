package pl.edu.pwr.student.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;

public class XOR extends BasicGate {
    public XOR(String type, PApplet s, PVector v) {
        super(type, s, v);
    }

    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        long trues = 0;
        for (SignalSender input : inputs)
            if (input.getState())
                ++trues;
        return trues % 2 == 1;
    }
}
