package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.NOT;
import pl.edu.pwr.student.Gates.Delay;
import pl.edu.pwr.student.IO.Output.Printer;
import pl.edu.pwr.student.Simulation;

public class CustomClock {
    public static void simulate() {
        System.out.println("FEEDBACK LOOP\n");

        NOT not = new NOT();
        Delay delay = new Delay(1000);

        not.connection(delay);
        delay.connection(not);

        Printer printer = new Printer("Clock");

        not.connection(printer);

        Simulation.simWait(10000);
        not.connection(delay);
    }
}
