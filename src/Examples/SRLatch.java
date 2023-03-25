package src.Examples;

import src.Gates.BasicGates.NOR;
import src.IO.Input.Switch;
import src.IO.Output.LED;

import static src.Simulation.simWait;

public class SRLatch {
    public static void simulate() {
        System.out.println("SR LATCH\n");

        Switch R;/*─────→*/NOR norUp;/*─┬───→*/LED out;
        //                   ↑          │
        //                 ┌─│──────────┘
        //                 ↓ └───────────┐
        Switch S;/*─────→*/NOR norDown;//┘

        R = new Switch();
        S = new Switch();

        norUp = new NOR();
        norDown = new NOR();

        out = new LED("SR Latch", 1000);

        R.connection(norUp);

        S.connection(norDown);

        norDown.connection(norUp);

        norUp.connection(norDown);
        norUp.connection(out);

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
