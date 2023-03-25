package src.Gates;

import org.jetbrains.annotations.NotNull;
import src.IO.Input.SignalSender;
import src.IO.Output.SignalReceiver;
import src.IO.VirtualIO;

import java.util.HashMap;
import java.util.HashSet;

public class CompoundGate {
    private String[] inputKeys;
    private String[] outputKeys;
    private final HashMap<String, SignalReceiver> inputs = new HashMap<>();
    private final HashMap<String, SignalSender> outputs = new HashMap<>();
    private HashSet<Compoundable> logic;

    public String[] getInputs() {
        return inputKeys;
    }
    public String[] getOutputs() {
        return outputKeys;
    }

    public SignalReceiver input(String name) {
        return inputs.get(name);
    }
    public SignalSender output(String name) {
        return outputs.get(name);
    }
    public HashSet<Compoundable> getGates() {
        return new HashSet<>(logic);
    }

    private void create(@NotNull HashSet<Compoundable> gates) {
        HashMap<Compoundable, Compoundable> copiedGates = new HashMap<>();
        for (Compoundable gate : gates)
            copiedGates.put(gate, gate.getNewInstance());

        for (Compoundable gate : gates) {
            HashSet<SignalReceiver> gateOutputs = gate.getOutputs();
            for (SignalReceiver receiver : gateOutputs)
                //noinspection SuspiciousMethodCalls
                copiedGates.get(gate).connection((SignalReceiver) copiedGates.get(receiver));
        }

        logic = new HashSet<>(copiedGates.values());

        HashSet<String> tempInputs = new HashSet<>();
        HashSet<String> tempOutputs = new HashSet<>();

        for (Compoundable gate : logic) {
            if (!gate.isIO())
                continue;

            VirtualIO ioGate = (VirtualIO) gate;
            if (!gate.hasInputs()) {
                inputs.put(ioGate.name, ioGate);
                tempInputs.add(ioGate.name);
            }
            else if (gate.getOutputs().size() == 0) {
                outputs.put(ioGate.name, ioGate);
                tempOutputs.add(ioGate.name);
            }
        }

        inputKeys = tempInputs.toArray(new String[0]);
        outputKeys = tempOutputs.toArray(new String[0]);
    }

    public CompoundGate(@NotNull CompoundGate gate) {
        create(gate.getGates());
    }

    public CompoundGate(@NotNull HashSet<Compoundable> gates) {
        create(gates);
    }

    public CompoundGate(HashSet<Compoundable> basicGates, @NotNull HashSet<CompoundGate> compGates) {
        for (CompoundGate gate : compGates)
            basicGates.addAll(gate.getGates());
        create(basicGates);
    }
}
