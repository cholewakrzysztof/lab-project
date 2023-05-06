package pl.edu.pwr.student.IO.Input;

import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

/**
 * Class representing a signal sender in a circuit.
 */
public abstract class SignalSender {
    /**
     * Default constructor
     */
    public SignalSender() {}

    /**
     * The set of all {@link SignalReceiver} connected to this SignalSender.
     */
    private final HashSet<SignalReceiver> outputs = new HashSet<>();

    /**
     * The current state of this SignalSender.
     */
    protected boolean state = false;

    /**
     * Returns a copy of the set of output {@link SignalReceiver}.
     *
     * @return the set of output {@link SignalReceiver}.
     */
    public HashSet<SignalReceiver> getOutputs() {
        return new HashSet<>(outputs);
    }

    /**
     * Returns the current state of the signal sender.
     *
     * @return the current state of the signal sender.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Attempts to connect the given {@link SignalReceiver} to this signal sender if they aren't connect.
     * Attempts to disconnect the given {@link SignalReceiver} from this signal sender if they had already been connected.
     *
     * @param receiver the {@link SignalReceiver} to (dis)connect.
     */
    public void connection(SignalReceiver receiver) {
        if (this == receiver)
            return;

        if (outputs.contains(receiver)) {
            outputs.remove(receiver);
            if (receiver.attemptDisconnect(this)) {
                receiver.update();
                return;
            }
            outputs.add(receiver);
        } else {
            outputs.add(receiver);
            if (receiver.attemptConnect(this)) {
                receiver.update();
                return;
            }
            outputs.remove(receiver);
        }
    }

    /**
     * Disconnects all output receivers from this signal sender.
     */
    public void disconnectOutputs() {
        HashSet<SignalReceiver> tempOut = new HashSet<>(outputs);
        for (SignalReceiver output : tempOut) {
            outputs.remove(output);
            output.attemptDisconnect(this);
            output.update();
        }

        if (!outputs.isEmpty())
            throw new RuntimeException("Error disconnecting outputs");
    }
    /**
     * Disconnects all input and output receivers from this signal sender.
     */
    public void fullDisconnect() {
        disconnectOutputs();
    }

    /**
     * Checks if the given signal receiver is currently connected to this signal sender.
     *
     * @param receiver the signal receiver to check.
     * @return true if the given signal receiver is currently connected to this signal sender, false otherwise.
     */
    public boolean isConnected(SignalReceiver receiver) {
        return outputs.contains(receiver);
    }

    /**
     * Sends an update signal to all output receivers of this signal sender.
     */
    protected void sendUpdate() {
        try {
            for (SignalReceiver output : outputs)
                output.update();
        } catch(ConcurrentModificationException e) {
            this.sendUpdate();
        }
    }
}
