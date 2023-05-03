package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.IO.Output.DebugPrinter;
import pl.edu.pwr.student.Simulation;

public class CustomClock {
    public static void simulate() {
        System.out.println("FEEDBACK LOOP\n");

        NOT not = new NOT();
        Delay delay = new Delay(1000);

        not.connection(delay);
        delay.connection(not);

        DebugPrinter printer = new DebugPrinter("Clock");

        not.connection(printer);

        Simulation.simWait(10000);
        not.connection(delay);
    }
}
