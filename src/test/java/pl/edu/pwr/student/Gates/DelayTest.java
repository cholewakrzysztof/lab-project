package pl.edu.pwr.student.Gates;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class DelayTest {
    protected Switch FirstInput = new Switch();
    protected Delay delay = new Delay(100);

    protected void setUp() {
        if(FirstInput.getState())
            FirstInput.toggle();

        FirstInput.connection(delay);
    }

    /*
     * Check state before updating state
     */
    @Test
    public void testStateBeforeUpdate() {
        FirstInput.toggle();

        simWait(50);
        assertFalse(delay.getState());
    }

    /*
     * Check state after updating state
     */
    @Test
    public void testStateAfterUpdate() {
        FirstInput.toggle();

        simWait(150);
        assertFalse(delay.getState());
    }
}