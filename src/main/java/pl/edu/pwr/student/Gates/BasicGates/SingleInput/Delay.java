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
    private long milliseconds;

    /**
     * Changes the delay of this element.
     *
     * @param delayMilliseconds delay to which the element should be set
     */
    public void setDelay(long delayMilliseconds) {
        milliseconds = delayMilliseconds;
    }

    /**
     * Updates the state of the element by running it in a new thread.
     * Overrides the default method of {@link BasicPassThrough} class to add the delay to calculations.
     */
    @Override
    public void run() {
        boolean newState = checkState();
        Simulation.simWait(milliseconds);
        if (state != newState) {
            state = newState;
            this.sendUpdate();
        }
    }
}
