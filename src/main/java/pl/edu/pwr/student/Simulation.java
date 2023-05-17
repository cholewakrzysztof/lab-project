package pl.edu.pwr.student;

import pl.edu.pwr.student.Examples.*;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.FileManagement.DataReader;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import uibooster.UiBooster;

import java.io.File;

/**
 * Main class of the application
 */
public class Simulation {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private Simulation(){}

    /**
     * Private constructor to prevent creating instances of this class
     *
     * @param milliseconds - time to wait in milliseconds
     */
    public static void simWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Main method of the application
     * @param args - arguments passed to the application
     */
    public static void main(String[] args) {
        try {
            Canvas canvas = new Canvas();
            //Registering example compound gates
            canvas.registerCompoundGate("DLatch", "", Register.createDLatch());
            canvas.registerCompoundGate("Register", "", Register.createRegister());
            canvas.registerCompoundGate("SRLatch", "", Register.createSRLatch());
            canvas.registerCompoundGate("4bitRegister", "", Register.create4bitRegister());
            canvas.registerCompoundGate("ETDFlipFlop", "", Register.createETDFlipFlop());

            DataReader.initCompoundGates(new File("gates"),canvas);
        } catch (Exception e) {
            new UiBooster().showException(
                    "An error occurred",
                    "Exception message",
                    e
            );
        }


//        // CODE EXAMPLES BELOW
//
//        // Synchronous
//        DLatch.simulate();
//        simWait(1000);
//        System.out.print("\n\n");
//
//        // Asynchronous
//        SRLatch.simulate();
//        simWait(1000);
//        System.out.print("\n\n");
//
//        // Feedback loop
//        CustomClock.simulate();
//        simWait(1000);
//        System.out.print("\n\n");
//
//        // Compound Gate 4 bit register
//        Register.simulate();
//        simWait(1000);
//        System.out.println("\n\n");
    }
}
