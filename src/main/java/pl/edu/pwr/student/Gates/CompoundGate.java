package pl.edu.pwr.student.Gates;

import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;

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

    public void fullDisconnect() {
        for (String inputKey : inputKeys)
            inputs.get(inputKey).disconnectInputs();

        for (String outputKey : outputKeys)
            outputs.get(outputKey).disconnectOutputs();
    }

    public CompoundGate(@NotNull CompoundGate gate) {
        create(gate.getGates());
    }

    public CompoundGate(@NotNull HashSet<Compoundable> gates) {
        create(gates);
    }

    public CompoundGate(HashSet<Compoundable> basicGates, @NotNull HashSet<CompoundGate> compGates) {
        HashSet<Compoundable> gates = new HashSet<>(basicGates);
        for (CompoundGate gate : compGates)
            gates.addAll(gate.getGates());
        this.create(gates);
    }
}
