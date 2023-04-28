package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

public class AND extends BasicGate {
    public AND() {}

    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (!input.getState())
                return false;
        return true;
    }
}
