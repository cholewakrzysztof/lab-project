package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

/**
 * The `NOR` gate class represents a logical NOR gate, which returns false if any of its inputs are true.
 * This class extends the {@link BasicGate} class and implements the `checkState` method to perform the NOR operation on the inputs.
 */
public class NOR extends BasicGate {
    /**
     * Default constructor of the gate.
     */
    public NOR() {}

    /**
     * Computes the output state of the `NOR` gate based on the input signals.
     *
     * @param inputs a set of signal senders connected to this gate's inputs
     * @return false if any input signals are true, true otherwise
     */
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (input.getState())
                return false;
        return true;
    }
}
