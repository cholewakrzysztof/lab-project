package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.NOR;
import pl.edu.pwr.student.IO.Input.DebugButton;
import pl.edu.pwr.student.IO.Output.DebugLED;

import static pl.edu.pwr.student.Simulation.simWait;

public class SRLatch {
    public static void simulate() {
        System.out.println("SR LATCH\n");

        DebugButton R;/*─────→*/NOR norUp;/*─┬───→*/
        DebugLED out;
        //                   ↑          │
        //                 ┌─│──────────|
        //                 ↓ └───────────|
        DebugButton S;/*─────→*/NOR norDown;//|

        R = new DebugButton();
        S = new DebugButton();

        norUp = new NOR();
        norDown = new NOR();

        out = new DebugLED("SR Latch", 1000);

        R.connection(norUp);

        S.connection(norDown);

        norDown.connection(norUp);

        norUp.connection(norDown);
        norUp.connection(out);

        R.press(10);
        simWait(100);

        out.toggle();

        simWait(2000);

        S.press(10);
        System.out.println("Pressed set");
        simWait(2000);

        R.press(10);
        System.out.println("Pressed reset");
        simWait(2000);

        R.press(10);
        System.out.println("Pressed reset again");
        simWait(2000);

        S.press(10);
        System.out.println("Pressed set");
        simWait(2000);

        S.press(10);
        System.out.println("Pressed set again");
        simWait(2000);

        out.toggle();
    }
}
