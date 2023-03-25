package src.Examples;

import src.Gates.BasicGates.NAND;
import src.Gates.BasicGates.NOT;
import src.IO.Input.Switch;
import src.IO.Output.LED;

import static src.Simulation.simWait;

public class DLatch {
    public static void simulate() {
        Switch data; /*──────┬───→*/ NAND nandLU; /*───→*/ NAND nandRU; /*┬──→*/ LED Q;
        //                   │       ↑                        ↑           │
        //                   │  ┌────┘                      ┌─│───────────┘
        //                   │  │                           ↓ └────────────┐
        Switch enable; /*────│──┴→*/ NAND nandLD; /*───→*/ NAND nandRD; //─┘
        //                   ↓       ↑
                        NOT not; //──┘

        data = new Switch();
        enable = new Switch();

        nandLU = new NAND();
        nandLD = new NAND();

        nandRU = new NAND();
        nandRD = new NAND();

        not = new NOT();

        System.out.println("D LATCH\n");

        Q = new LED("D Latch", 1000);

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

        Q.toggle();

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

        Q.toggle();
    }
}
