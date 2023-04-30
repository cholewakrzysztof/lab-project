package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

/**
 * The 'NOT' class represents a logical NOT gate, which returns true if the number of true inputs is odd and false otherwise.
 * This class extends the {@link pl.edu.pwr.student.Gates.BasicGates.MultipleInput.BasicGate} class and implements the `checkState` method to perform the NOT operation on the input.
 */
public class NOT extends BasicPassThrough {
    /**
     * Default constructor of the gate.
     */
    public NOT() {}

    /**
     * Checks the state of the 'NOT' gate based on the input signals.
     *
     * @param inputState state of {@link pl.edu.pwr.student.IO.Input.SignalSender} connected to this gate's input
     * @return true if the input is false, false otherwise
     */
    protected boolean checkState(boolean inputState) {
        return !inputState;
    }
}
