package pl.edu.pwr.student.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashSet;

public class XOR extends BasicGate implements UiAvailable {
    public XOR() {}
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        long trues = 0;
        for (SignalSender input : inputs)
            if (input.getState())
                ++trues;
        return trues % 2 == 1;
    }
}
