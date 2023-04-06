package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.NOT;
import pl.edu.pwr.student.Gates.Delay;
import pl.edu.pwr.student.IO.Output.Printer;

public class CustomClock {
    public static void simulate() {
        NOT not = new NOT();
        Delay delay = new Delay(1000);

        not.connection(delay);
        delay.connection(not);

        Printer printer = new Printer("Clock");

        not.connection(printer);
    }
}
