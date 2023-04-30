package pl.edu.pwr.student.IO.Output;

import pl.edu.pwr.student.Simulation;

/**
 * Debug class that prints the state of its input in regular intervals.
 */
public class DebugLED extends BasicReceiver implements Runnable {
    /**
     * Creates a new object of this class.
     *
     * @param name the name of the created object - printed in regular intervals
     * @param updateMilliseconds how often(in milliseconds) the name and state of this element should be printed
     */
    public DebugLED(String name, long updateMilliseconds) {
        this.name = name;
        milliseconds = updateMilliseconds;

        thread = new Thread(this);
    }

    /**
     * Name of this element.
     */
    private final String name;
    /**
     * Interval at which the name and state are printed out to the console.
     */
    private long milliseconds;
    /**
     * Variable on which depends the object is actively printing out its name and state to the console.
     */
    private boolean power = false;
    /**
     * Thread in which the printing method is called.
     */
    private final Thread thread;

    /**
     * Method that toggles the power of the object.
     *
     * @return whether the element is power after called the method or not
     */
    public boolean toggle() {
        if (milliseconds < 1) {
            power = false;
            return false;
        }

        power = !power;
        if (power)
            thread.start();
        else
            thread.interrupt();
        return power;
    }

    /**
     * Method that prints the name and state in regular intervals.
     * NOT TO BE CALLED MANUALLY - Handled by the Thread class.
     */
    public void run() {
        while (power) {
            System.out.println(name + ": " + state);
            Simulation.simWait(milliseconds);
        }
    }

    /**
     * Method that changes how often the objects prints to the console.
     *
     * @param updateMilliseconds new interval in milliseconds
     */
    public void changeUpdateFreq(long updateMilliseconds) {
        milliseconds = updateMilliseconds;

        if (milliseconds < 1)
            toggle();
    }
}
