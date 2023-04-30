package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

import pl.edu.pwr.student.Simulation;

/**
 * The `Delay` class represents a logical element that adds a set amount of delay to the signal that travels through it.
 * This class extends the {@link BasicPassThrough} class.
 */
public class Delay extends BasicPassThrough {
    /**
     * Creates the 'Delay' element and sets the amount of delay.
     * @param delayMilliseconds amount of delay(in milliseconds) to create the element with
     */
    public Delay(long delayMilliseconds) {
        milliseconds = delayMilliseconds;
    }

    /**
     * Amount of delay this element adds.
     */
    private final long milliseconds;

    /**
     * Method that updates the state of the element by running it in a new thread.
     * Overrides the default method of {@link BasicPassThrough} class to add the delay to calculations.
     */
    @Override
    public void update() {
        Simulation.simWait(milliseconds);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Computes the output state of the 'Delay' element.
     *
     * @param inputState state of {@link pl.edu.pwr.student.IO.Input.SignalSender} connected to this gate's input
     * @return the state of the inputState from the set amount of milliseconds ago
     */
    protected boolean checkState(boolean inputState) {
        return inputState;
    }
}
