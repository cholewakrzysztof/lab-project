package pl.edu.pwr.student.IO.Output;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.IO.Input.SignalSender;

/**
 * An abstract class that represents a basic receiver with a single input and no outputs.
 */
public abstract class BasicReceiver implements SignalReceiver {
    /**
     * Input signal connected to this gate.
     * {@link SignalSender}
     */
    protected SignalSender input = null;

    /**
     * The current state of this receiver.
     */
    protected boolean state = false;

    /**
     * Returns the current state of this receiver.
     *
     * @return the current state of this receiver
     */
    public boolean getState() {
        return state;
    }

    /**
     * Method that updates the state of the receiver by running it in a new thread.
     */
    public void update() {
        boolean newState = false;

        if (input != null)
            newState = input.getState();

        if (state == newState)
            return;

        state = newState;
        react();
    }

    /**
     * Attempts to connect a {@link SignalSender} to this element.
     * Should not be called manually - will not complete anything and return false.
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
     * Attempts to disconnect a {@link SignalSender} from this receiver.
     * Should not be called manually - will not complete anything and return false.
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
     * Method that disconnects all input signals connected to this gate.
     */
    public void disconnectInputs() {
        if (input != null)
            input.connection(this);
        if (input != null)
            throw new RuntimeException("Error disconnecting input");
    }
    /**
     * Method that fully disconnects this gate by disconnecting all input and output signals.
     * Necessary to completely remove a gate from simulation and allow it to be collected by the garbage collector.
     */
    public void fullDisconnect() {
        disconnectInputs();
    }

    /**
     * Method that allows different receivers to react differently to being triggered.
     */
    protected void react() {}
}