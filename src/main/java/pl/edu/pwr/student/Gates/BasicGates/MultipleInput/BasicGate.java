package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.Simulation;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

/**
 * Represents a basic logic gate with multiple inputs and a single output.
 */
public abstract class BasicGate extends SignalSender implements SignalReceiver, Compoundable, Runnable, UiAvailable {
    /**
     * Default constructor.
     */
    public BasicGate() {}

    /**
     * Set of {@link SignalSender} connected to this gate.
     */
    private final HashSet<SignalSender> inputs = new HashSet<>();

    /**
     * Returns the inputs(of type {@link SignalSender}) to this element.
     *
     * @return set of inputs(of type {@link SignalSender}) to this element
     */
    public HashSet<SignalSender> getInputs() {
        return new HashSet<>(inputs);
    }
    
    /**
     * Updates the state of the gate by running it in a new thread.
     */
    public void update() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Attempts to connect a {@link SignalSender} to this gate.
     *
     * @param sender the {@link SignalSender} to connect
     * @return true if the connection was successful, false otherwise
     */
    public boolean attemptConnect(SignalSender sender) {
        if (this == sender)
            return false;

        if (!sender.isConnected(this))
            return false;

        inputs.add(sender);
        return true;
    }
    /**
     * Attempts to disconnect a {@link SignalSender} from this gate.
     *
     * @param sender the {@link SignalSender} to disconnect
     * @return true if the disconnection was successful, false otherwise
     */
    public boolean attemptDisconnect(@NotNull SignalSender sender) {
        if (sender.isConnected(this))
            return false;

        inputs.remove(sender);
        return true;
    }

    /**
     * Disconnects all input signals connected to this gate.
     */
    public void disconnectInputs() {
        HashSet<SignalSender> tempInputs = new HashSet<>(inputs);
        for (SignalSender input : tempInputs)
            input.connection(this);
        if (!inputs.isEmpty())
            throw new RuntimeException("Error disconnecting inputs");
    }

    /**
     * Fully disconnects this gate by disconnecting all input and output signals.
     * Necessary to completely remove a gate from simulation and allow it to be collected by the garbage collector.
     */
    public void fullDisconnect() {
        super.disconnectOutputs();
        this.disconnectInputs();
    }

    /**
     * Runs the gate logic to update the state and sends an update signal if the state has changed.
     * NOT TO BE CALLED MANUALLY - Handled by the Thread class.
     */
    public void run() {
        boolean oldState = state;
        if (!inputs.isEmpty())
            try {
                state = checkState(inputs);
            } catch (ConcurrentModificationException e) {
                Simulation.simWait(100);
                state = checkState(inputs);
            }

        if (oldState != state)
            sendUpdate();
    }

    /**
     * Checks what the current state of the gate should be.
     *
     * @param inputs the set of {@link SignalSender} connected to this gate
     * @return new state of the gate
     */
    protected abstract boolean checkState(HashSet<SignalSender> inputs);
}
