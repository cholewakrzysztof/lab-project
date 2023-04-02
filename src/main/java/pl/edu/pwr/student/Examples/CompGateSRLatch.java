package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.NOR;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.VirtualIO;

import java.util.HashSet;

import static pl.edu.pwr.student.Simulation.simWait;

public class CompGateSRLatch {
    public static void simulate() {
        System.out.println("2x SR LATCH USING COMPOUND GATES\n");

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

        R1.press(10);
        R2.press(10);

        LED out1 = new LED("out1", 1000);
        gate1.output("Out").connection(out1);

        LED out2 = new LED("out2", 1000);
        gate2.output("Out").connection(out2);

        R1.press(10);
        R2.press(10);

        simWait(100);

        out1.toggle();
        simWait(100);
        out2.toggle();
        simWait(2000);

        S1.press(10);
        System.out.println("Pressed S1");
        simWait(2000);

        R1.press(10);
        S2.press(10);
        System.out.println("Pressed R1 and S2");
        simWait(3000);

        out1.toggle();
        out2.toggle();
    }
}
