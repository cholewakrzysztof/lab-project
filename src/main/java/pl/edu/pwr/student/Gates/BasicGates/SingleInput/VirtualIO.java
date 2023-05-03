package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

import pl.edu.pwr.student.Gates.BasicGates.Compoundable;

/**
 * A virtual input/output class that extends {@link BasicPassThrough}.
 * This class represents a virtual input/output port in a {@link pl.edu.pwr.student.Gates.CompoundGate}.
 */
public class VirtualIO extends BasicPassThrough {
    /**
     * Constructs a new VirtualIO object with the given name.
     *
     * @param name the name of the virtual input/output port
     */
    public VirtualIO(String name) {
        this.name = name;
    }

    /**
     * Name of the virtual input/output port.
     */
    public final String name;

    /**
     * Returns true since this is a virtual input/output port.
     *
     * @return true
     */
    @Override
    public boolean isIO() {
        return true;
    }

    /**
     * Returns a new instance of VirtualIO with the same name.
     *
     * @return a new instance of VirtualIO with the same name
     */
    @Override
    public Compoundable getNewInstance() {
        return new VirtualIO(name);
    }

    /**
     * Computes the output state of the 'VirtualIO' element.
     *
     * @param inputState state of {@link pl.edu.pwr.student.IO.Input.SignalSender} connected to this gate's input
     * @return the state of the inputState
     */
    @Override
    protected boolean checkState(boolean inputState) {
        return inputState;
    }
}
