package pl.edu.pwr.student.Gates.BasicGates;

import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

/**
 * An interface for gates that can be used in a compound gate.
 */
public interface Compoundable {
    /**
     * Returns the set of {@link SignalReceiver} of the gate.
     *
     * @return the set of {@link SignalReceiver} of the gate.
     */
    HashSet<SignalReceiver> getOutputs();

    /**
     * Returns a new instance of the gate.
     *
     * @return a new instance of the gate.
     */
    default Compoundable getNewInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        return null;
    }

    /**
     * Connects a {@link SignalReceiver} to the output of the gate.
     *
     * @param receiver the {@link SignalReceiver} to connect.
     */
    void connection(SignalReceiver receiver);

    /**
     * Fully disconnects the gate.
     * Necessary to completely remove a gate from simulation and allow it to be collected by the garbage collector.
     */
    void fullDisconnect();

    /**
     * Returns whether the gate is an input/output gate.
     *
     * @see pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO
     * @return true if the gate is an input/output gate, false otherwise.
     */
    default boolean isIO() {
        return false;
    }
}
