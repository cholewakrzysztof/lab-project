package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

/**
 * The `OR` gate class represents a logical OR gate, which returns true if any of its inputs are true.
 * This class extends the {@link BasicGate} class and implements the `checkState` method to perform the OR operation on the inputs.
 */
public class OR extends BasicGate {
    /**
     * Default constructor of the gate.
     */
    public OR() {}

    /**
     * Computes the output state of the `OR` gate based on the input signals.
     *
     * @param inputs a set of signal senders connected to this gate's inputs
     * @return true if any input signals are true, false otherwise
     */
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (input.getState())
                return true;
        return false;
    }
}
