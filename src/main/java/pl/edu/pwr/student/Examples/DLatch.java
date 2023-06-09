package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.NAND;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.IO.Input.DebugButton;
import pl.edu.pwr.student.IO.Output.Speaker;

import static pl.edu.pwr.student.Simulation.simWait;

public class DLatch {
    public static void simulate() {
        DebugButton data; /*──────┬───→*/ NAND nandLU; /*───→*/ NAND nandRU; /*┬──→*/ Speaker Q;
        //                   │       ↑                        ↑           │
        //                   │  ┌────|                      ┌─│───────────|
        //                   │  │                           ↓ └────────────|
        DebugButton enable; /*────│──┴→*/ NAND nandLD; /*───→*/ NAND nandRD; //─|
        //                   ↓       ↑
                        NOT not; //──|

        data = new DebugButton();
        enable = new DebugButton();

        nandLU = new NAND();
        nandLD = new NAND();

        nandRU = new NAND();
        nandRD = new NAND();

        not = new NOT();

        System.out.println("D LATCH\n");

        Q = new Speaker();

        data.connection(nandLU);
        data.connection(not);

        enable.connection(nandLU);
        enable.connection(nandLD);

        not.connection(nandLD);

        nandLU.connection(nandRU);

        nandLD.connection(nandRD);

        nandRU.connection(Q);
        nandRU.connection(nandRD);

        nandRD.connection(nandRU);

        enable.press(10);
        simWait(100);

        simWait(2000);

        data.press(10);
        System.out.println("Pressed data without enable");
        simWait(2000);

        data.press(20);
        enable.press(10);
        System.out.println("Pressed enable and data");
        simWait(2000);

        data.press(10);
        System.out.println("Pressed data without enable");
        simWait(2000);

        enable.press(10);
        System.out.println("Pressed enable with data off/low");
        simWait(2000);
    }
}
