package pl.edu.pwr.student.Gates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiAvailable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a gate that is composed of other gates.
 * Can be used to combine multiple gates into a single logical unit.
 */
public class CompoundGate implements CreatableInstance, UiAvailable {

    public final String name;

    /**
     * Copy constructor.
     *
     * @param gate the gate to copy
     */
    public CompoundGate(@NotNull final CompoundGate gate) {
        this.name = gate.name;
        create(gate.getGates());
    }

    /**
     * Creates a new CompoundGate that is composed of the specified gates that implement {@link Compoundable}.
     *
     * @param name name of compound gate
     * @param gates the gates to include in the compound gate
     */
    public CompoundGate(final String name, @NotNull final HashSet<Compoundable> gates) {
        this.name = name;
        create(gates);
    }

    /**
     * Creates a new CompoundGate that is composed of the specified basic gates which implement {@link Compoundable} and compound gates.
     *
     * @param name name of compound gate
     * @param basicGates basic gates to include in the compound gate
     * @param compGates compound gates to include in the compound gate
     */
    public CompoundGate(final String name, final HashSet<Compoundable> basicGates, @NotNull final HashSet<CompoundGate> compGates) {
        this.name = name;
        HashSet<Compoundable> gates = new HashSet<>(basicGates);
        for (CompoundGate gate : compGates)
            gates.addAll(gate.getGates());
        this.create(gates);
    }

    /**
     * Array of the names of inputs of this gate.
     */
    private String[] inputKeys;
    /**
     * Array of the names of outputs of this gate.
     */
    private String[] outputKeys;

    /**
     * Collection of inputs of this gate.
     * {@link SignalReceiver}
     */
    private final HashMap<String, SignalReceiver> inputs = new HashMap<>();
    /**
     * Set of outputs of this gate.
     * {@link SignalSender}
     */
    private final HashMap<String, SignalSender> outputs = new HashMap<>();

    /**
     * Set of logic elements composing this compound gate.
     * {@link Compoundable}
     */
    private HashSet<Compoundable> logic;

    /**
     * Moves selected String in the array of inputs or outputs to a different place.
     * Doesn't change the order of other objects in the array.
     *
     * @param key index of the String you want to move
     * @param place spot where the String at the key's place should be
     * @param IO either "Input" or "Output", depending on the order of which array you want to be modified
     */
    public void setOrder(final int key, final int place, @NotNull final String IO) {
        if (key < 0)
            throw new IllegalArgumentException("Key cannot be negative");

        final String[] keys;

        if (IO.equals("Input"))
            keys = inputKeys;
        else if (IO.equals("Output"))
            keys = outputKeys;
        else
            throw new IllegalArgumentException("IO parameter not set properly");

        if (keys.length < place || keys.length < key)
            throw new IllegalArgumentException("Place and key arguments cannot exceed the size of the selected array");

        String[] newKeys = Arrays.copyOf(keys, keys.length);

        if (place > key) {
            for (int i = key + 1; i < place + 1; ++i)
                newKeys[i - 1] = newKeys[i];
        } else {
            for (int i = key - 1; i > place - 1; --i)
                newKeys[i + 1] = newKeys[i];
        }
        newKeys[place] = keys[key];

        if (IO.equals("Input"))
            inputKeys = newKeys;
        else
            outputKeys = newKeys;
    }

    /**
     * Returns an array of the names of the inputs to this gate.
     *
     * @return an array of input names
     */
    public String[] getInputKeys() {
        return inputKeys;
    }

    /**
     * Returns an array of the names of the outputs of this gate.
     *
     * @return an array of output names
     */
    public String[] getOutputKeys() {
        return outputKeys;
    }

    /**
     * Returns the input with the specified name.
     *
     * @param name the name of the input to retrieve
     * @return the {@link SignalReceiver} with the specified name
     */
    public SignalReceiver input(final String name) {
        return inputs.get(name);
    }

    /**
     * Returns the output with the specified name.
     *
     * @param name the name of the output to retrieve
     * @return the {@link SignalSender} with the specified name
     */
    public SignalSender output(final String name) {
        return outputs.get(name);
    }

    @Override
    public HashSet<SignalReceiver> getOutputs() {
        return UiAvailable.super.getOutputs();
    }

    /**
     * Disconnects all inputs and outputs of this compound gate.
     * Allows the gate to be completely removed from the simulation and collected by the garbage collector.
     */
    public void fullDisconnect() {
        for (String inputKey : inputKeys)
            inputs.get(inputKey).disconnectInputs();

        for (String outputKey : outputKeys)
            outputs.get(outputKey).disconnectOutputs();
    }

    @Override
    public void connection(final SignalReceiver receiver) {}

    /**
     * Returns a set of the gates that make up this compound gate.
     * {@link Compoundable}
     *
     * @return a set of gates
     */
    public HashSet<Compoundable> getGates() {
        return new HashSet<>(logic);
    }

    /**
     * Fully creates the logic and IO of this compound gate.
     * Compound gates that should make up this gate are to be treated as separate connected gates that compose them(using CompoundGate.getGates() method) and implement {@link Compoundable}.
     *
     * @param gates logic elements that are to make up this compound gate
     */
    private void create(@NotNull final HashSet<Compoundable> gates) {
        HashMap<Compoundable, Compoundable> copiedGates = new HashMap<>();
        for (Compoundable gate : gates)
            copiedGates.put(gate, (Compoundable) gate.getNewInstance());

        for (Compoundable gate : gates)
            for (SignalReceiver receiver : gate.getOutputs())
                //noinspection SuspiciousMethodCalls
                copiedGates.get(gate).connection((SignalReceiver) copiedGates.get(receiver));

        logic = new HashSet<>(copiedGates.values());
        HashSet<Compoundable> toRemove = new HashSet<>();

        for (Compoundable gate : logic) {
            for (SignalReceiver receiver : gate.getOutputs()) {
                Compoundable compReceiver = (Compoundable) receiver;
                if (compReceiver.isIO() && compReceiver.getOutputs().size() != 0) {
                    HashSet<SignalReceiver> tempReceivers = new HashSet<>(compReceiver.getOutputs());
                    compReceiver.fullDisconnect();
                    toRemove.add(compReceiver);
                    for (SignalReceiver tempReceiver : tempReceivers)
                        gate.connection(tempReceiver);
                }
            }
        }

        for (Compoundable gate : toRemove)
            logic.remove(gate);

        HashSet<String> tempInputs = new HashSet<>();
        HashSet<String> tempOutputs = new HashSet<>();

        for (Compoundable gate : logic) {
            if (!gate.isIO())
                continue;

            VirtualIO ioGate = (VirtualIO) gate;
            if (!ioGate.hasInputs()) {
                if (inputs.containsKey(ioGate.name))
                    throw new RuntimeException("Compound gates cannot have multiple inputs with the same name");
                inputs.put(ioGate.name, ioGate);
                tempInputs.add(ioGate.name);
            }
            if (gate.getOutputs().size() == 0) {
                if (outputs.containsKey(ioGate.name))
                    throw new RuntimeException("Compound gates cannot have multiple outputs with the same name");
                outputs.put(ioGate.name, ioGate);
                tempOutputs.add(ioGate.name);
            }
        }

        inputKeys = tempInputs.toArray(new String[0]);
        outputKeys = tempOutputs.toArray(new String[0]);
    }

    @Override
    public CreatableInstance getNewInstance() {
        try {
            return this.getClass().getDeclaredConstructor(Class.forName("pl.edu.pwr.student.Gates.CompoundGate")).newInstance(this);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 ClassNotFoundException ignored) {}
        return null;
    }
}
