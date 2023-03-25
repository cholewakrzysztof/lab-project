package src.Gates.BasicGates;

import org.jetbrains.annotations.NotNull;
import src.Gates.BasicGate;
import src.IO.Input.SignalSender;

import java.util.HashSet;

public class AND extends BasicGate {
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (!input.getState())
                return false;
        return true;
    }
}
