package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

import java.util.HashSet;

/**
 * The 'XNOR' class represents a logical XNOR gate, which returns true if the number of true inputs is odd and false otherwise.
 * This class extends the {@link BasicGate} class and implements the `checkState` method to perform the XNOR operation on the inputs.
 */
public class XNOR extends BasicGate {
    /**
     * Default constructor of the gate.
     */
    public XNOR() {}

    /**
     * Checks the state of the 'XOR' gate based on the input signals.
     *
     * @param inputs a set of signal senders connected to this gate's inputs
     * @return true if the number of true inputs is even, false otherwise
     */
    @Override
    protected boolean checkState(@NotNull HashSet<SignalSender> inputs) {
        long trues = 0;
        for (SignalSender input : inputs)
            if (input.getState())
                ++trues;
        return trues % 2 == 0;
    }
}
