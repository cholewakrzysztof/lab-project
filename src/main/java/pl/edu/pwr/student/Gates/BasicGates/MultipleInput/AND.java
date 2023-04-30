package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

/**
 * The `AND` gate class represents a logical AND gate, which returns true if and only if all of its inputs are true.
 * This class extends the {@link BasicGate} class and implements the `checkState` method to perform the AND operation on the inputs.
 */
public class AND extends BasicGate {
    /**
     * Default constructor of the gate.
     */
    public AND() {}

    /**
     * Computes the output state of the `AND` gate based on the input signals.
     *
     * @param inputs a set of signal senders connected to this gate's inputs
     * @return true if all input signals are true, false otherwise
     */
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (!input.getState())
                return false;
        return true;
    }
}
