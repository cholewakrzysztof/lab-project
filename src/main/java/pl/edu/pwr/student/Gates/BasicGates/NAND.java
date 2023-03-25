package pl.edu.pwr.student.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;

public class NAND extends BasicGate {
    public NAND(String type, PApplet s, PVector v) {
        super(type, s, v);
    }

    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (!input.getState())
                return true;
        return false;
    }
}
