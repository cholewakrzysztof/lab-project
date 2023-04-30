package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.HashSet;

/**
 * Abstract class that represents a basic gate with multiple inputs and a single output.
 * Extends {@link SignalSender} and implements {@link SignalReceiver}, {@link Compoundable} and Runnable.
 */
public abstract class BasicGate extends SignalSender implements SignalReceiver, Compoundable, Runnable {
    /**
     * Constructor for the BasicGate class.
     */
    public BasicGate() {}

    /**
     * Set of {@link SignalSender} connected to this gate.
     */
    private final HashSet<SignalSender> inputs = new HashSet<>();

    /**
     * Method that updates the state of the gate by running it in a new thread.
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
     * Method that disconnects all input signals connected to this gate.
     */
    public void disconnectInputs() {
        HashSet<SignalSender> tempInputs = new HashSet<>(inputs);
        for (SignalSender input : tempInputs)
            input.connection(this);
        if (!inputs.isEmpty())
            throw new RuntimeException("Error disconnecting inputs");
    }

    /**
     * Method that fully disconnects this gate by disconnecting all input and output signals.
     */
    public void fullDisconnect() {
        super.disconnectOutputs();
        this.disconnectInputs();
    }

    /**
     * Method that runs the gate logic to update the state and sends an update signal if the state has changed.
     */
    public void run() {
        boolean oldState = state;
        if (!inputs.isEmpty())
            state = checkState(inputs);

        if (oldState != state)
            sendUpdate();
    }

    /**
     * Method that checks what the current state of the gate should be.
     *
     * @param inputs the set of {@link SignalSender} connected to this gate
     * @return new state of the gate
     */
    protected abstract boolean checkState(HashSet<SignalSender> inputs);
}

