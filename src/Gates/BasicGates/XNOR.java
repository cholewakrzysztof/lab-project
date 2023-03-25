package src.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import src.Gates.BasicGate;
import src.IO.Input.SignalSender;

import java.util.HashSet;

public class XNOR extends BasicGate {
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        long trues = 0;
        for (SignalSender input : inputs)
            if (input.getState())
                ++trues;
        return trues % 2 == 0;
    }
}
