package pl.edu.pwr.student.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashSet;

public class NOR extends BasicGate implements UiAvailable {
    public NOR() {}

    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (input.getState())
                return false;
        return true;
    }
}
