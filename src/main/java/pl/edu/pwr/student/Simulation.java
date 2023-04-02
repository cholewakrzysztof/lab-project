package pl.edu.pwr.student;

import pl.edu.pwr.student.Examples.*;
import pl.edu.pwr.student.Gates.*;
import pl.edu.pwr.student.Gates.BasicGates.*;
import pl.edu.pwr.student.IO.Input.*;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.IO.VirtualIO;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import java.util.*;

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


        // CODE EXAMPLES BELOW

        // Synchronous
        DLatch.simulate();
        System.out.print("\n\n");

        // Asynchronous
        SRLatch.simulate();
        System.out.print("\n\n");

        // Compound Gate SR Latch
        CompGateSRLatch.simulate();

//        AND and = new AND();
//        NOT not = new NOT();
//
//        LED printer = new LED("test", 500);
//
//        and.connection(not);
//        not.connection(and);
//
//        and.connection(printer);
//
//        printer.toggle();
    }
}
