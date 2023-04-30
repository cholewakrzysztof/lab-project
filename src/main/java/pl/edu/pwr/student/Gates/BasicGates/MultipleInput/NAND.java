package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

/**
 * The `NAND` gate class represents a logical NAND gate, which returns false if and only if all of its inputs are true.
 * This class extends the {@link BasicGate} class and implements the `checkState` method to perform the NAND operation on the inputs.
 */
public class NAND extends BasicGate {
    /**
     * Default constructor of the gate.
     */
    public NAND() {}

    /**
     * Computes the output state of the `NAND` gate based on the input signals.
     *
     * @param inputs a set of signal senders connected to this gate's inputs
     * @return false if all input signals are true, true otherwise
     */
    @Override
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        for (SignalSender input : inputs)
            if (!input.getState())
                return true;
        return false;
    }
}
