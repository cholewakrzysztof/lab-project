package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiAvailable;

/**
 * Represents a basic pass through with a single input and a single output.
 */
public abstract class BasicPassThrough extends SignalSender implements SignalReceiver, Compoundable, Runnable, UiAvailable {
    /**
     * Default constructor..
     */
    public BasicPassThrough() {}

    /**
     * Input signal connected to this gate.
     * {@link SignalSender}
     */
    private SignalSender input;

    /**
     * Checks if this gate has any inputs connected.
     * @return true if any inputs are connected, false otherwise
     */
    public boolean hasInputs() {
        return input != null;
    }

    /**
     * Updates the state of the gate by running it in a new thread.
     */
    public void update() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Attempts to connect a {@link SignalSender} to this element.
     *
     * @param sender the {@link SignalSender} to connect
     * @return true if the connection was successful, false otherwise
     */
    public boolean attemptConnect(SignalSender sender) {
        if (input != null)
            return false;

        if (!sender.isConnected(this))
            return false;

        input = sender;
        return true;
    }
    /**
     * Attempts to disconnect a {@link SignalSender} from this element.
     *
     * @param sender the {@link SignalSender} to disconnect
     * @return true if the disconnection was successful, false otherwise
     */
    public boolean attemptDisconnect(@NotNull SignalSender sender) {
        if (input != sender)
            return false;

        if (sender.isConnected(this))
            return false;

        input = null;
        return true;
    }

    /**
     * Disconnects all input signals connected to this gate.
     */
    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }
    /**
     * Fully disconnects this gate by disconnecting all input and output signals.
     * Necessary to completely remove a gate from simulation and allow it to be collected by the garbage collector.
     */
    public void fullDisconnect() {
        super.disconnectOutputs();
        disconnectInputs();
    }

    /**
     * Runs the gate logic to update the state and sends an update signal if the state has changed.
     * NOT TO BE CALLED MANUALLY - Handled by the Thread class.
     */
    public void run() {
        boolean oldState = state;
        if (input != null)
            state = checkState(input.getState());

        if (oldState != state)
            sendUpdate();
    }

    /**
     * Checks what the currect state of the gate should be.
     *
     * @param inputState the input signal connected to this gate
     * @return new state of the gate
     */
    protected abstract boolean checkState(boolean inputState);
}
