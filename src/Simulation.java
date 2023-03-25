package src;

import src.Examples.DLatch;
import src.Examples.SRLatch;
import src.Gates.BasicGates.*;
import src.Gates.CompoundGate;
import src.Gates.Compoundable;
import src.IO.Input.SignalSender;
import src.IO.Input.Switch;
import src.IO.Output.LED;
import src.IO.Output.SignalReceiver;
import src.IO.VirtualIO;

import java.util.HashMap;
import java.util.HashSet;

public class Simulation {
    public static void simWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        // All basic gates(AND OR XOR NAND NOR XNOR NOT)
        // are to be added to this collection when created by the user
        // and removed from it when deleted from the simulation
        HashSet<Compoundable> basicGates = new HashSet<>();

        // All compound gates that are currently in simulation should be put in this collection
        HashSet<CompoundGate> compoundGates = new HashSet<>();

        // When a new compound gate is created by the user it should be added to this collection
        // The key is the name of the gate given by the user during its creation
        // When adding new compound gates to the simulation use 'compoundGates.add(new CompoundGate(savedCompoundGates.get(nameOfTheGate)));'
        HashMap<String, CompoundGate> savedCompoundGates = new HashMap<>();

        // All inputs and clocks in the simulation are to be in this collection
        HashSet<SignalSender> userInputs = new HashSet<>();

        // All outputs in the simulation are to be in this collection
        HashSet<SignalReceiver> systemOutputs = new HashSet<>();


        // TESTING & EXAMPLES BELOW

        // Synchronous
        DLatch.simulate();
        System.out.print("\n\n");

        // Asynchronous
        SRLatch.simulate();
        System.out.print("\n\n");

        // Compound Gate SR Latch

        HashSet<Compoundable> gates = new HashSet<>();

        VirtualIO R = new VirtualIO("R");
        VirtualIO S = new VirtualIO("S");

        NOR norUp = new NOR();
        NOR norDown = new NOR();

        VirtualIO out = new VirtualIO("Out");

        norUp.connection(out);
        norUp.connection(norDown);

        norDown.connection(norUp);

        R.connection(norUp);
        S.connection(norDown);

        gates.add(R);
        gates.add(S);
        gates.add(norUp);
        gates.add(norDown);
        gates.add(out);

        CompoundGate SRL = new CompoundGate(gates);

        CompoundGate gate1 = new CompoundGate(SRL);
        Switch R1 = new Switch();
        Switch S1 = new Switch();

        R1.connection(gate1.input("R"));
        S1.connection(gate1.input("S"));

        CompoundGate gate2 = new CompoundGate(SRL);
        Switch R2 = new Switch();
        Switch S2 = new Switch();

        R2.connection(gate2.input("R"));
        S2.connection(gate2.input("S"));

        LED out1 = new LED("out1", 1000);
        gate1.output("Out").connection(out1);

        LED out2 = new LED("out2", 1000);
        gate2.output("Out").connection(out2);

        out1.toggle();
        out2.toggle();
        simWait(2000);

        S1.press(100);
        simWait(2000);

        R1.press(100);
        S2.press(100);
        simWait(2000);

        out1.toggle();
        out2.toggle();
    }
}
