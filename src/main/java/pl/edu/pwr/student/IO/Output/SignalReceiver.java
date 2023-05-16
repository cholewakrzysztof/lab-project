package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.Gates.CreatableInstance;
import pl.edu.pwr.student.IO.Input.SignalSender;

/**
 * Interface for a signal receiver which can connect to a {@link SignalSender} and receive updates.
 */
public interface SignalReceiver extends CreatableInstance {
    /**
     * Gets the current state of the signal receiver.
     *
     * @return the current state of the signal receiver
     */
    boolean getState();

    /**
     * Updates the state of the signal receiver based on its input(s).
     */
    void update();

    /**
     * Attempts to connect the signal receiver to a given {@link SignalSender}.
     * Should not be called manually - will not complete anything and return false.
     *
     * @param sender the {@link SignalSender} to connect to
     * @return {@code true} if the connection was successful, {@code false} otherwise
     */
    boolean attemptConnect(SignalSender sender);

    /**
     * Attempts to disconnect the signal receiver from a given {@link SignalSender}.
     * Should not be called manually - will not complete anything and return false.
     *
     * @param sender the {@link SignalSender} to disconnect from
     * @return {@code true} if the disconnection was successful, {@code false} otherwise
     */
    boolean attemptDisconnect(SignalSender sender);

    /**
     * Disconnects all inputs of the signal receiver.
     */
    void disconnectInputs();

    /**
     * Fully disconnects the signal receiver from all inputs and outputs.
     * Necessary to completely remove a gate from simulation and allow it to be collected by the garbage collector.
     */
    void fullDisconnect();
}
