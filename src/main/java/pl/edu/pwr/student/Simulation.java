package pl.edu.pwr.student;

//import pl.edu.pwr.student.Examples.DLatch;
//import pl.edu.pwr.student.Examples.SRLatch;
import pl.edu.pwr.student.Gates.BasicGates.*;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.VirtualIO;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.HashSet;

public class Simulation {
    public static void simWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Gates-Simulation"};
        Canvas Canvas = new Canvas();

        PApplet.runSketch(processingArgs, Canvas);
        ShapeLoader.loadShapes(Canvas);

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

//
//        // TESTING & EXAMPLES BELOW
//
//        // Synchronous
//        DLatch.simulate();
//        System.out.print("\n\n");
//
//        // Asynchronous
//        SRLatch.simulate();
//        System.out.print("\n\n");
//
//        // Compound Gate SR Latch
//
//        HashSet<Compoundable> gates = new HashSet<>();
//
//        VirtualIO R = new VirtualIO("R");
//        VirtualIO S = new VirtualIO("S");
//
//        NOR norUp = new NOR();
//        NOR norDown = new NOR();
//
//        VirtualIO out = new VirtualIO("Out");
//
//        norUp.connection(out);
//        norUp.connection(norDown);
//
//        norDown.connection(norUp);
//
//        R.connection(norUp);
//        S.connection(norDown);
//
//        gates.add(R);
//        gates.add(S);
//        gates.add(norUp);
//        gates.add(norDown);
//        gates.add(out);
//
//        CompoundGate SRL = new CompoundGate(gates);
//
//        CompoundGate gate1 = new CompoundGate(SRL);
//        Switch R1 = new Switch();
//        Switch S1 = new Switch();
//
//        R1.connection(gate1.input("R"));
//        S1.connection(gate1.input("S"));
//
//        CompoundGate gate2 = new CompoundGate(SRL);
//        Switch R2 = new Switch();
//        Switch S2 = new Switch();
//
//        R2.connection(gate2.input("R"));
//        S2.connection(gate2.input("S"));
//
//        R1.press(10);
//        R2.press(10);
//
//        LED out1 = new LED("out1", 1000);
//        gate1.output("Out").connection(out1);
//
//        LED out2 = new LED("out2", 1000);
//        gate2.output("Out").connection(out2);
//
//        out1.toggle();
//        out2.toggle();
//        simWait(3000);
//
//        S1.press(10);
//        System.out.println("Pressed S1");
//        simWait(2000);
//
//        R1.press(10);
//        S2.press(10);
//        System.out.println("Pressed R1 and S2");
//        simWait(2000);
//
//        out1.toggle();
//        out2.toggle();
    }
}
